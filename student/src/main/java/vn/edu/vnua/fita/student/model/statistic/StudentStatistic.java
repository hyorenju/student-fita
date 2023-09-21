package vn.edu.vnua.fita.student.model.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class StudentStatistic {
    private List<AvgPoint4> avgPoint4List;
    private List<AvgPoint10> avgPoint10List;
    private List<TrainingPoint> trainingPointList;
    private Integer creditsAcc;
    private Integer totalCredits;
    private Float accPoint4;

    @Data
    @Builder
    public static class AvgPoint4 {
        private String termId;
        private Float point;
    }

    @Data
    @Builder
    public static class AvgPoint10 {
        private String termId;
        private Float point;
    }

    @Data
    @Builder
    public static class TrainingPoint {
        private String termId;
        private Integer point;
    }
}
