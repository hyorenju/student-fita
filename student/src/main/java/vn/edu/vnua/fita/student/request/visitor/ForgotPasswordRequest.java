package vn.edu.vnua.fita.student.request.visitor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Truyền mã user id vào nè")
    private String id;

    private ChangePasswordValue values;

    @Data
    @AllArgsConstructor
    public static class ChangePasswordValue{
        @NotBlank(message = "Vui lòng nhập mật khẩu mới")
        private String newPassword;

        @NotBlank(message = "Vui lòng xác nhận mật khẩu")
        private String confirmPassword;
    }
}
