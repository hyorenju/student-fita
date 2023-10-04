package vn.edu.vnua.fita.student.service.visitor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.visitor.ForgotPasswordRequest;
import vn.edu.vnua.fita.student.request.visitor.SendMailRequest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final String userError = "Tài khoải hoặc email không trùng khớp";
    private final String userNotFound = "Không tìm thấy người dùng";

    @Value("${spring.mail.properties.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.mail.properties.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Override
    public void sendMessage(SendMailRequest request){
        if(request.getUser().getId().matches("^[a-zA-Z][a-zA-Z0-9]*$")){
            Admin admin = adminRepository.findById(request.getUser().getId()).orElseThrow(() -> new RuntimeException(userNotFound));
            if(admin.getEmail().equals(request.getUser().getEmail())){
                String verificationToken = generateVerificationToken(admin.getId());
                mailService.sendEmailHtml(request.getUser().getEmail(), request.getLink(), verificationToken);
            } else {
                throw new RuntimeException(userError);
            }
        } else if(request.getUser().getId().matches("^[0-9]+")){
            Student student = studentRepository.findById(request.getUser().getId()).orElseThrow(() -> new RuntimeException(userNotFound));
            if(student.getEmail().equals(request.getUser().getEmail())){
                String verificationToken = generateVerificationToken(student.getId());
                mailService.sendEmailHtml(request.getUser().getEmail(), request.getLink(), verificationToken);
            } else {
                throw new RuntimeException(userError);
            }
        } else {
            throw new RuntimeException(userError);
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

    private String generateVerificationToken(String id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(id)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }
}
