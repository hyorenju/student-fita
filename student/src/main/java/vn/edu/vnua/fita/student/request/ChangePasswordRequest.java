package vn.edu.vnua.fita.student.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Truyền mã user id vào nè")
    private String id;

    private ChangePasswordValue values;

    @Data
    @AllArgsConstructor
    public static class ChangePasswordValue{
        @NotNull(message = "Vui lòng nhập mật khẩu hiện tại")
        private String currentPassword;

        @NotNull(message = "Vui lòng nhập mật khẩu mới")
        private String newPassword;

        @NotNull(message = "Vui lòng xác nhận mật khẩu mới")
        private String confirmPassword;
    }
}
