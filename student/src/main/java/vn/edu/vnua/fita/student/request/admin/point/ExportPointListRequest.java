package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ExportPointListRequest {
    private String studentId;
    private String termId;

    private GetPointListRequest.FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private int point;
        private int accPoint;
        private int trainingPoint;
    }
}
