package vn.edu.vnua.fita.student.request.admin.student_status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;
import vn.edu.vnua.fita.student.request.admin.point.GetPointListRequest;

@Data
public class GetStudentStatusListRequest extends GetPageBaseRequest {
    private String studentId;

    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String statusId;
        private String termId;
    }
}
