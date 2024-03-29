package vn.edu.vnua.fita.student.dto;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.SchoolYear;

@Data
public class PointOfYearDTO {
    private Long id;
    private StudentDTO student;
    private SchoolYearDTO year;
    private Float avgPoint10;
    private Float avgPoint4;
    private Float trainingPoint;
    private Float pointAcc10;
    private Float pointAcc4;
    private Integer creditsRegistered;
    private Integer creditsPassed;
    private Integer creditsNotPassed;
    private Integer creditsAcc;
}
