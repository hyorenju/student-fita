package vn.edu.vnua.fita.student.model.statistic;

import lombok.Builder;
import lombok.Data;
import vn.edu.vnua.fita.student.model.statistic.chartfrom.GroupedColumnChart;

import java.util.List;

@Data
@Builder
public class FacultyColumnChart {
    private List<GroupedColumnChart> chart;
}
