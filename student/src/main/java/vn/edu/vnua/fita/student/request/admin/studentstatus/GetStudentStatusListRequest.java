package vn.edu.vnua.fita.student.request.admin.studentstatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;

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
