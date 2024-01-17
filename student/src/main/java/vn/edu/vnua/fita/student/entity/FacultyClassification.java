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
@Table(name = "faculty_term")
public class FacultyClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @Column(name = "forced_out")
    private Integer forcedOut;

    @Column(name = "dropout_with_permission")
    private Integer dropoutWithPermission;

    @Column(name = "excellent")
    private Integer excellent;

    @Column(name = "good")
    private Integer good;

    @Column(name = "fair")
    private Integer fair;

    @Column(name = "medium")
    private Integer medium;

    @Column(name = "weak")
    private Integer weak;

    @Column(name = "worst")
    private Integer worst;
}
