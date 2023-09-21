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
@Table(name = "dropout_statistics")
public class DropoutStatistic {
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
}
