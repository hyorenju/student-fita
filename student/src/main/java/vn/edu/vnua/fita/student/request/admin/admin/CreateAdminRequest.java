package vn.edu.vnua.fita.student.request.admin.admin;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class CreateAdminRequest {
    @NotBlank(message = "Tài khoản của trị viên không được để trống")
    @Size(max = 10, message = "Tài khoản của trị viên chỉ được tối đa 10 ký tự")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "Tài khoản của trị viên phải bắt đầu bằng ký tự chữ, không được chứa dấu cách và ký tự đặc biệt")
    private String id;

    @NotBlank(message = "Tên quản trị viên không được để trống")
    @Size(max = 200, message = "Tên quản trị viên chỉ được tối đa 200 ký tự")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Tên quản trị viên không được chứa ký tự đặc biệt")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 32, message = "Mật khẩu chỉ được tối đa 32 ký tự")
    private String password;

    @NotBlank(message = "Vai trò không được để trống")
    private String roleId;
}
