package vn.edu.vnua.fita.student.request.admin.studentstatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ExportStudentStatusRequest {
    private String studentId;

    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String statusId;
        private String termId;
    }
}
