package vn.edu.vnua.fita.student.model.statistic;

import lombok.Data;

@Data
public class Classification {
    private String termId;
    private Integer excellent;
    private Integer good;
    private Integer fair;
    private Integer medium;
    private Integer weak;
    private Integer worst;
}
