package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String title;

    @Column(length = 1000)
    private String link;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "post_date")
    private Timestamp postDate;

    @Column(length = 200)
    private String type;

    @Column(name = "is_checked")
    private Boolean isChecked;

}
