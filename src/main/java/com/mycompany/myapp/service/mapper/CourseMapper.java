package com.mycompany.myapp.service.mapper;

// course -> course DTO

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.service.dto.CourseDTO;
import java.util.List;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class CourseMapper {

    public static CourseDTO convert(Course course) {
        return CourseDTO
            .builder()
            .courseName(course.getCourseName())
            .courseContent(course.getCourseContent())
            .courseLocation(course.getCourseLocation())
            .teacherId(course.getTeacherId())
            .build();
    }
}
