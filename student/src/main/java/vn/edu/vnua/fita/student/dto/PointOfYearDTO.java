package vn.edu.vnua.fita.student.dto;

import lombok.Data;

@Data
public class PointOfYearDTO {
    private Long id;
    private StudentDTO student;
    private String year;
    private Float avgPoint10;
    private Float avgPoint4;
    private Integer trainingPoint;
    private Float pointAcc10;
    private Float pointAcc4;
    private Integer creditsRegistered;
    private Integer creditsPassed;
    private Integer creditsNotPassed;
    private Integer creditsAcc;
    private Boolean isDeleted;
}
