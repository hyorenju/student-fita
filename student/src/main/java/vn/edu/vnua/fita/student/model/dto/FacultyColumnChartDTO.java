package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;
import vn.edu.vnua.fita.student.model.statistic.chartfrom.GroupedColumnChart;

import java.util.List;

@Data
public class FacultyColumnChartDTO {
    private List<GroupedColumnChart> chart;
}
