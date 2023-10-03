package vn.edu.vnua.fita.student.request.admin.admin;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class UpdateProfileRequest {
    @NotBlank(message = "Tên quản trị viên không được để trống")
    @Size(max = 200, message = "Tên quản trị viên chỉ được tối đa 200 ký tự")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;
}
