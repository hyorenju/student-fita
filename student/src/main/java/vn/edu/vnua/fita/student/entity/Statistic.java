package vn.edu.vnua.fita.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @Column(name = "dropout_without_permission")
    private Integer dropoutWithoutPermission;
    @Column(name = "dropout_with_permission")
    private Integer dropoutWithPermission;

    @Column(name = "course_excellent")
    private Integer courseExcellent;
    @Column(name = "course_good")
    private Integer courseGood;
    @Column(name = "course_fair")
    private Integer courseFair;
    @Column(name = "course_medium")
    private Integer courseMedium;
    @Column(name = "course_weak")
    private Integer courseWeak;
    @Column(name = "course_worst")
    private Integer courseWorst;

    @Column(name = "major_excellent")
    private Integer majorExcellent;
    @Column(name = "major_good")
    private Integer majorGood;
    @Column(name = "major_fair")
    private Integer majorFair;
    @Column(name = "major_medium")
    private Integer majorMedium;
    @Column(name = "major_weak")
    private Integer majorWeak;
    @Column(name = "major_worst")
    private Integer majorWorst;

    @Column(name = "class_excellent")
    private Integer classExcellent;
    @Column(name = "class_good")
    private Integer classGood;
    @Column(name = "class_fair")
    private Integer classFair;
    @Column(name = "class_medium")
    private Integer classMedium;
    @Column(name = "class_weak")
    private Integer classWeak;
    @Column(name = "class_worst")
    private Integer classWorst;
}
