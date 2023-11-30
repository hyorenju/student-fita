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

    @OneToOne
    @JoinColumn(name = "monitor")
    private Student monitor;

    @OneToOne
    @JoinColumn(name = "vice_monitor")
    private Student viceMonitor;

    @OneToOne
    @JoinColumn(name = "secretary")
    private Student secretary;

    @OneToOne
    @JoinColumn(name = "deputy_secretary")
    private Student deputySecretary;

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL)
    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "classes_terms",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;
}
