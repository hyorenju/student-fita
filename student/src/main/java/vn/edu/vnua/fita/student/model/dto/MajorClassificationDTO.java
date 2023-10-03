package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;
import vn.edu.vnua.fita.student.model.statistic.CircleChart;

import java.util.List;

@Data
public class MajorClassificationDTO {
    private MajorDTO major;
    private TermDTO term;
    private List<CircleChart> chart;
}
