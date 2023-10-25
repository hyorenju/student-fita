package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ExportPointListRequest {
    private String studentId;

    private GetPointListRequest.FilterCondition filter;

    private GetPointListRequest.SortCondition sort;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String termId;
        private String classId;
        private int point;
        private int accPoint;
        private int trainingPoint;
    }

    @Data
    @RequiredArgsConstructor
    public static class SortCondition {
        private Boolean sortAvgPoint10;
        private Boolean sortAvgPoint4;
        private Boolean sortTrainingPoint;
        private Boolean sortPointAcc10;
        private Boolean sortPointAcc4;
    }
}
