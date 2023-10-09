package vn.edu.vnua.fita.student.model.statistic.chartfrom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupedColumnChart {
    private String name;
    private String termId;
    private Integer quantity;
}