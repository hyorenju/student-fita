package vn.edu.vnua.fita.student.request.admin.course;

import lombok.Data;
import jakarta.validation.constraints.*;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;


@Data
public class GetCourseListRequest extends GetPageBaseRequest {
    private String id;
}
