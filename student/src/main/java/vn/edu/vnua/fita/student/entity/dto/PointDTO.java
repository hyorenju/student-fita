package vn.edu.vnua.fita.student.entity.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PointDTO {
    private Long id;
    private String studentId;
    private String surname;
    private String lastName;
    private String termId;
    private Float avgPoint10;
    private Float avgPoint4;
    private Integer trainingPoint;
    private Integer creditsAcc;
    private Float pointAcc10;
    private Float pointAcc4;
    private Boolean isDeleted;
}
