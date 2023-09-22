package vn.edu.vnua.fita.student.model.entity;

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
@Table(name = "majors_terms")
public class MajorClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

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
