package vn.edu.vnua.fita.student.request.admin.student_status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.InputDate;

@Data
public class UpdateStudentStatusRequest {
    @NotBlank(message = "Thời gian không được để trống")
    @InputDate(message = "Thời gian phải đúng định dạng dd/MM/yyyy")
    @Size(min = 8, max = 10, message = "Thời gian chỉ được 8-10 ký tự")
    private String time;

    private String note;
}
