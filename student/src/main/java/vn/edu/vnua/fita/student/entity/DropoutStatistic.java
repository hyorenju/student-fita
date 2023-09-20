package vn.edu.vnua.fita.student.entity;

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
@Table(name = "dropout-statistics")
public class DropoutStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
