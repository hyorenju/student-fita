package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;

@Data
public class GetPointListRequest extends GetPageBaseRequest {
    private String studentId;
    private String termId;

    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private int point;
        private int accPoint;
        private int trainingPoint;
    }
}
