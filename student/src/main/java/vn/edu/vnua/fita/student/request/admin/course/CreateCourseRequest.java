package vn.edu.vnua.fita.student.request.admin.course;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class CreateCourseRequest {
    @NotBlank(message = "Mã khóa không được để trống")
    @Pattern(regexp = "^\\d+$", message = "Mã khóa phải là một số tự nhiên")
    private String id;
}
