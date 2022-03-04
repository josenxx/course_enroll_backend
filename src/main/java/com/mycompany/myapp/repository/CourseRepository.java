package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// model class of table
// primary key data type
public interface CourseRepository extends JpaRepository<Course, Long> {
    // 根据JPA命名规则命名函数，hibernate 就能根据函数的名字生成对应的function
    Optional<Course> findOneByCourseName(String courseName);
}
