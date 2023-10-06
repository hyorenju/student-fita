package vn.edu.vnua.fita.student.model.statistic.chartfrom;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CircleChart {
    private String type;
    private Integer value;
}
