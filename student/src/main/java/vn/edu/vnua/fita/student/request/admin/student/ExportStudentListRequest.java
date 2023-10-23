package vn.edu.vnua.fita.student.request.admin.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@AllArgsConstructor
public class ExportStudentListRequest{
    private String studentId;
    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String courseId;
        private String majorId;
        private String classId;
        private String familySituation;
    }
}
