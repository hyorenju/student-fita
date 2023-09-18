package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students_terms")
public class Point{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", length = 100)
    private String studentId;

    @Column(length = 200)
    private String surname;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "term_id", length = 100)
    private String termId;

    @Column(name = "average_point_10")
    private Float avgPoint10;

    @Column(name = "average_point_4")
    private Float avgPoint4;

    @Column(name = "training_point")
    private Integer trainingPoint;

    @Column(name = "credits_accumulated")
    private Integer creditsAcc;

    @Column(name = "point_accumulated_10")
    private Float pointAcc10;

    @Column(name = "point_accumulated_4")
    private Float pointAcc4;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
