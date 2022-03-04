package com.mycompany.myapp.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "course")
@Data // getter and setter
public class Course {

    @Id // primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_location")
    private String courseLocation;

    @Column(name = "course_content")
    private String courseContent;

    @Column(name = "teacher_Id")
    private Long teacherId;
}
