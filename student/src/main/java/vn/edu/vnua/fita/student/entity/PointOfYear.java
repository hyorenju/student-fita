package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import vn.edu.vnua.fita.student.domain.validator.ImportPointValidator;
import vn.edu.vnua.fita.student.model.file.PointExcelData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "point_annual")
public class PointOfYear{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(length = 200)
    private String year;

    @Column(name = "average_point_10")
    private Float avgPoint10;

    @Column(name = "average_point_4")
    private Float avgPoint4;

    @Column(name = "training_point")
    private Integer trainingPoint;

    @Column(name = "point_accumulated_10")
    private Float pointAcc10;

    @Column(name = "point_accumulated_4")
    private Float pointAcc4;

    @Column(name = "credits_registered")
    private Integer creditsRegistered;

    @Column(name = "credits_passed")
    private Integer creditsPassed;

    @Column(name = "credits_not_passed")
    private Integer creditsNotPassed;

    @Column(name = "credits_accumulated")
    private Integer creditsAcc;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
