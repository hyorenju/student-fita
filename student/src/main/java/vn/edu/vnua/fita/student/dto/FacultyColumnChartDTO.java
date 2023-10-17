package vn.edu.vnua.fita.student.dto;

import lombok.Data;
import vn.edu.vnua.fita.student.model.statistic.chartform.GroupedColumnChart;

import java.util.List;

@Data
public class FacultyColumnChartDTO {
    private List<GroupedColumnChart> chart;
}
