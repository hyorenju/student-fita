package vn.edu.vnua.fita.student.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "school_years")
public class SchoolYear {
    @Id
    @Column(name = "id", length = 200)
    private String id;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
    private Collection<PointOfYear> pointOfYears;
}
