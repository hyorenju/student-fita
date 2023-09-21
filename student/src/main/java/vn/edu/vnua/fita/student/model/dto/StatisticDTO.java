package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;

@Data
public class StatisticDTO {
    private Long id;

    private TermDTO term;

    private Integer dropoutWithoutPermission;
    private Integer dropoutWithPermission;

    private Integer courseExcellent;
    private Integer courseGood;
    private Integer courseFair;
    private Integer courseMedium;
    private Integer courseWeak;
    private Integer courseWorst;

    private Integer majorExcellent;
    private Integer majorGood;
    private Integer majorFair;
    private Integer majorMedium;
    private Integer majorWeak;
    private Integer majorWorst;

    private Integer classExcellent;
    private Integer classGood;
    private Integer classFair;
    private Integer classMedium;
    private Integer classWeak;
    private Integer classWorst;
}
