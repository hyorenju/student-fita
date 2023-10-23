package vn.edu.vnua.fita.student.request.admin.student;

import vn.edu.vnua.fita.student.request.GetPageBaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class GetStudentListRequest extends GetPageBaseRequest {
    private String studentId;
    private FilterCondition filter = new FilterCondition();

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String courseId;
        private String majorId;
        private String classId;
        private String familySituation;
    }
}
