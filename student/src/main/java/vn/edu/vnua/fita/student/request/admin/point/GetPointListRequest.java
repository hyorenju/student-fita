package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;

@Data
public class GetPointListRequest extends GetPageBaseRequest {
    private String studentId;

    private FilterCondition filter;
    private SortCondition sort;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        //
        private String termId;
        private String classId;
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
