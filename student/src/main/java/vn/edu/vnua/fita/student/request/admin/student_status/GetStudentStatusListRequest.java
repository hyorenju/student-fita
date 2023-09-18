package vn.edu.vnua.fita.student.request.admin.student_status;

import lombok.Data;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;

@Data
public class GetStudentStatusListRequest extends GetPageBaseRequest {
    private String studentId;
    private String statusId;
}
