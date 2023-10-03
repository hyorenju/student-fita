package vn.edu.vnua.fita.student.model.statistic;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.fita.student.model.entity.AClass;
import vn.edu.vnua.fita.student.model.entity.Term;

import java.util.List;

@Data
@NoArgsConstructor
public class ClassChart {
    private AClass aclass;
    private Term term;
    private List<CircleChart> chart;
}
