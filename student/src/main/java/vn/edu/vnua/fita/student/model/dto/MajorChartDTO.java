package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;
import vn.edu.vnua.fita.student.model.statistic.chartfrom.CircleChart;

import java.util.List;

@Data
public class MajorChartDTO {
    private MajorDTO major;
    private TermDTO term;
    private List<CircleChart> chart;
}
