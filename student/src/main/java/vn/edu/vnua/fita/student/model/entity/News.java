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
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String title;

    @Column(length = 2000000000)
    private String content;

    @Column(name = "post_date")
    private Timestamp postDate;

    @Column(length = 200)
    private String type;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
