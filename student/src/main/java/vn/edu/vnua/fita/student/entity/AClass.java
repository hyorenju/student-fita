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
@Table(name = "classes")
public class AClass {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String name;

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL)
    private Collection<Student> students;
}
