package vn.edu.vnua.fita.student.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotNull(message = "Vui lòng nhập mật khẩu hiện tại")
    private String currentPassword;

    @NotNull(message = "Vui lòng nhập mật khẩu mới")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Mật khẩu phải có 8 ký tự trở lên, có cả ký tự chữ và số")
    private String newPassword;

    @NotNull(message = "Vui lòng xác nhận mật khẩu mới")
    private String confirmPassword;
}
