package vn.edu.vnua.fita.student.request.visitor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SendMailRequest {
    @NotBlank(message = "Front-end dev vui lòng truyền link trang đổi mật khẩu")
    private String link;

    private RequestBy user;

    @Data
    public static class RequestBy {
        @NotBlank(message = "Nhập tài khoản của bạn")
        private String id;

        @NotBlank(message = "Nhập email bạn đã đăng ký cho tài khoản trên")
        private String email;
    }
}
