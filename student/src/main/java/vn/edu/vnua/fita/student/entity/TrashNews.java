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
@Table(name = "trash_news")
public class TrashNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "news_id")
    private News news;

    @Column
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    private Admin deletedBy;
}
