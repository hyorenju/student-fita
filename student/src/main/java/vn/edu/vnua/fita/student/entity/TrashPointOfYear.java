package vn.edu.vnua.fita.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trash_point_annual")
public class TrashPointOfYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "point_annual_id")
    private PointOfYear pointOfYear;

    @Column
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    private Admin deletedBy;
}
