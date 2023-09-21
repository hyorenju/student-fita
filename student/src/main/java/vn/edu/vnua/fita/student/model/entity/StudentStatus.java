package vn.edu.vnua.fita.student.model.entity;

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
@Table(name = "students_statuses")
public class StudentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", length = 100)
    private String studentId;

    @Column(name = "surname", length = 200)
    private String surname;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "status_id", length = 100)
    private Integer statusId;

    @Column(name = "status_name", length = 200)
    private String statusName;

    @Column
    private Timestamp time;

    @Column(name = "term_id",length = 100)
    private String termId;

    @Column(length = 200)
    private String note;

}
