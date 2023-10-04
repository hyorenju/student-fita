package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.visitor.ForgotPasswordRequest;
import vn.edu.vnua.fita.student.request.visitor.SendMailRequest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;
    private final String userError = "Tài khoải hoặc email không trùng khớp";

    @Override
    public void sendMessage(SendMailRequest request){
//        if(!adminRepository.existsById(request.getId())
//        && !studentRepository.existsById(request.getId())){
//            throw new EntityNotFoundException("Tài khoản hoặc email không trùng khớp");
//        }

        if(adminRepository.existsById(request.getUser().getId())){
            Admin admin = adminRepository.findById(request.getUser().getId()).get();
            if(admin.equals(request.getUser().getEmail())){
                sendMimeEmail(request.getUser().getEmail(), request.getLink());
            } else {
                throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
            }
        } else if(studentRepository.existsById(request.getUser().getId())){
            Student student = studentRepository.findById(request.getUser().getId()).get();
            if(student.getEmail().equals(request.getUser().getEmail())){
                sendMimeEmail(request.getUser().getEmail(), request.getLink());
//                student.setPassword("123");
//                studentRepository.saveAndFlush(student);
            } else {
                throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
            }
        } else {
            throw new RuntimeException("Tài khoản hoặc email không trùng khớp");
        }
    }

    @Override
    public void changePassword(ForgotPasswordRequest request) {
        if(studentRepository.existsById(request.getId())) {
            Student student = studentRepository.findById(request.getId()).get();
            if (encoder.matches(request.getValues().getNewPassword(), student.getPassword())) {
                throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
            }
            if (!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())) {
                throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
            }
            student.setPassword(encoder.encode(request.getValues().getNewPassword()));
            studentRepository.saveAndFlush(student);
        } else if(adminRepository.existsById(request.getId())){
            Admin admin = adminRepository.findById(request.getId()).get();
            if(encoder.matches(request.getValues().getNewPassword(), admin.getPassword())){
                throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
            }
            if(!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())){
                throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
            }
            admin.setPassword(encoder.encode(request.getValues().getNewPassword()));
            adminRepository.saveAndFlush(admin);
        } else {
            throw new RuntimeException("Người dùng không tồn tại");
        }
    }

    private void sendMimeEmail(String email, String link) {

        final String username = "hyorenju@gmail.com";
        final String password = "yywetcrecogeyztq";

        Properties props = new Properties();
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String subject = "Yêu cầu đồi mật khẩu";
        String text = "Bạn đã gửi một yêu cầu đổi mật khẩu. Hãy truy cập đường dẫn sau để đổi mật khẩu:" +
                "\n\n" + link +
                "\n\nVui lòng không chia sẻ link này cho bất cứ ai nếu bạn không muốn người khác đổi mật khẩu của mình." +
                "\n\nNếu bạn không yêu cầu đổi mật khẩu, vui lòng bỏ qua bước này.";

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setSubject(subject);
//            message.setText(text);
//            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
