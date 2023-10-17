package vn.edu.vnua.fita.student.dto;

import lombok.Data;

@Data
public class PointDTO {
    private Long id;
    private StudentDTO student;
    private TermDTO term;
    private Float avgPoint10;
    private Float avgPoint4;
    private Integer trainingPoint;
    private Integer creditsAcc;
    private Float pointAcc10;
    private Float pointAcc4;
    private Boolean isDeleted;
}
