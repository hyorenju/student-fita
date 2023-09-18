package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "majors")
public class Major {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String name;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private Collection<Student> students;
}

