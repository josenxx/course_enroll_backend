package com.mycompany.myapp.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_course")
@Data
@NoArgsConstructor
public class UserCourse {

    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    @Id // primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ManyToOne
    private Course course;
}
