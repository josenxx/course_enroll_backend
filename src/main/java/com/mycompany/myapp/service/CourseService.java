package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor //lombok - generate all args constructor
public class CourseService {

    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private CourseMapper courseMapper;

    public void enrollCourse(String userName, String courseName) {
        //1. User exist?
        //2. Course exist?
        UserCourse userCourse = getUserCourse(userName, courseName); //user_id = 1, course_id = 2
        //3. UserCourse not exists? (de-dupe)
        Optional<UserCourse> optionalUserCourse = userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        optionalUserCourse.ifPresent(existingUserCourse -> {
            throw new IllegalArgumentException("UserCourse already exist: " + existingUserCourse.toString());
        });
        //4. Save new UserCourse to DB (user_course table)
        userCourseRepository.save(userCourse);
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        //        List<CourseDTO> courseDTOList = new ArrayList<>();
        //        for (Course course: courses) {
        //            courseDTOList.add(courseMapper.convert(course));
        //        }
        //        return courseDTOList;
        // Java Stream - Java 8 特性
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    public List<CourseDTO> getStudentCourses(String userName) {
        //1. User exist?
        User user = getUserByUserName(userName); //user_id = 1
        //2. Get List<UserCourse> from user_course table by User
        List<UserCourse> userCourseList = userCourseRepository.findAllByUser(user);
        //3. Extract Course from each UserCourse + Convert Course -> CourseDTO
        //        List<CourseDTO> courseDTOList = new ArrayList<>();
        //        for (UserCourse userCourse: userCourseList) {
        //            courseDTOList.add(courseMapper.convert(userCourse.getCourse()));
        //        }
        //        return courseDTOList;
        return userCourseList
            .stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.convert(course))
            .collect(Collectors.toList());
    }

    public void dropCourse(String userName, String courseName) {
        //1. User exist?
        //2. Course exist?
        UserCourse userCourse = getUserCourse(userName, courseName);
        //3. Delete and ensure UserCourse not exists.
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
    }

    private UserCourse getUserCourse(String userName, String courseName) {
        //1. User exist?
        User user = getUserByUserName(userName);
        //2. Course exist?
        Course course = getCourseByCourseName(courseName);
        //3. Return new UserCourse.
        return new UserCourse(user, course);
    }

    private User getUserByUserName(String userName) {
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user: " + userName));
    }

    private Course getCourseByCourseName(String courseName) {
        Optional<Course> optionalCourse = courseRepository.findOneByCourseName(courseName);
        return optionalCourse.orElseThrow(() -> new IllegalArgumentException("No such course: " + courseName));
    }
}
