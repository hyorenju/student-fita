package vn.edu.vnua.fita.student.model.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.fita.student.model.entity.Course;
import vn.edu.vnua.fita.student.model.entity.Term;
import vn.edu.vnua.fita.student.model.statistic.chartfrom.CircleChart;

import java.util.List;

@Data
@NoArgsConstructor
public class CourseChart {
    private Course course;
    private Term term;
    private List<CircleChart> chart;
}
