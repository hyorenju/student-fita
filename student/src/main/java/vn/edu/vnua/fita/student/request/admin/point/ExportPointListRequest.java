package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ExportPointListRequest {
    private String studentId;
    private String termId;
    private String classId;

    private FilterCondition filter;

    private SortCondition sort;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private int point;
        private int accPoint;
        private int trainingPoint;
    }

    @Data
    @RequiredArgsConstructor
    public static class SortCondition {
        private String sortColumn;
        private String sortType;
    }
}
