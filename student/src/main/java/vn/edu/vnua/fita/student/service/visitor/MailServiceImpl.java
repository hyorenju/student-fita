package vn.edu.vnua.fita.student.service.visitor;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmailText(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();

        String subject = "Yêu cầu đổi mật khẩu từ SV FITA";
        String text = "Bạn vừa yêu cầu đổi mật khẩu. Vui lòng nhấn vào link sau để thực hiện:\n" +
                link +
                "Nếu bạn không yêu cầu điều này, vui lòng bỏ qua tin nhắn.";

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Override
    public void sendEmailHtml(String to, String link, String token) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String subject = "Yêu cầu đổi mật khẩu từ SV FITA";
        String htmlContent = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body {" +
                "    font-family: Arial, sans-serif;" +
                "    text-align: center;" +
                "    margin: 0;" +
                "    padding: 0;" +
                "}" +
                ".container {" +
                "    max-width: 700px;" +
                "    display: flex;" +
                "    margin: 0 auto;" +
                "    justify-content: center;" +
                "    align-items: center;" +
                "    background-color: #f5f5f5;" +
                "}" +
                ".content {" +
                "    text-align: center;" +
                "    margin: 0 auto;" +
                "}" +
                ".button {" +
                "    background-color: #00984F;" +
                "    border: none;" +
                "    color: white;" +
                "    padding: 15px 32px;" +
                "    text-align: center;" +
                "    text-decoration: none;" +
                "    display: inline-block;" +
                "    font-size: 16px;" +
                "    margin: 4px 2px;" +
                "    cursor: pointer;" +
                "    border-radius: 8px;" +
                "    transition: background-color 0.3s;" +
                "}" +
                ".button:hover {" +
                "    background-color: #00532B;" +
                "}" +
                "h1 {" +
                "    color: #333;" +
                "}" +
                "p {" +
                "    color: #555;" +
                "    font-size: 15px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='content'>" +
                "<h1>Xác minh yêu cầu đổi mật khẩu</h1>" +
                "<p>Bạn vừa cho biết mình đang quên mật khẩu. Vui lòng nhấn vào nút bên dưới để tiến hành đổi mật khẩu:</p>" +
                "<p><a href='" + link + "?email-verification=" + token + "' style='text-decoration: none;'>" +
                "<button class='button' style='cursor: pointer;'>Đổi mật khẩu</button>" +
                "</a></p>" +
                "<p>Nếu bạn không yêu cầu điều này, vui lòng bỏ qua tin nhắn.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi gửi email: " + e.getMessage());
        }
    }
}
