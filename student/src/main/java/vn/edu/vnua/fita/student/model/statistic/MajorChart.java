package vn.edu.vnua.fita.student.model.statistic;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.Major;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.model.statistic.chartform.CircleChart;

import java.util.List;

@Data
public class MajorChart {
    private Major major;
    private Term term;
    private List<CircleChart> chart;
}
