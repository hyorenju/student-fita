package vn.edu.vnua.fita.student.service.visitor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.common.UserIdentifyPatternConstant;
import vn.edu.vnua.fita.student.domain.exception.TokenInvalid;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.visitor.ForgotPasswordRequest;
import vn.edu.vnua.fita.student.request.visitor.SendMailRequest;
import vn.edu.vnua.fita.student.security.JwtTokenProvider;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final String userError = "Tài khoải hoặc email không trùng khớp";
    private final String studentNotFound = "Không tìm thấy sinh viên %s trong hệ thống";
    private final String adminNotFound = "Không tìm thấy quản trị viên %s trong hệ thống";
    private final String notBeSame = "Mật khẩu mới không được trùng mật khẩu cũ";
    private final String mustMatch = "Xác nhận mật khẩu không trùng khớp";

    @Override
    public void sendMessage(SendMailRequest request) {
        String id = request.getUser().getId();
        if(id.matches(UserIdentifyPatternConstant.STUDENT_ID_PATTERN)) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));

            if (student.getEmail().equals(request.getUser().getEmail())) {
                String verificationToken = generateVerificationToken(student.getId());
                mailService.sendEmailHtml(request.getUser().getEmail(), request.getLink(), verificationToken);
            } else {
                throw new RuntimeException(userError);
            }
        } else if(id.matches(UserIdentifyPatternConstant.ADMIN_ID_PATTERN)) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, id)));;
            if(admin.getEmail().equals(request.getUser().getEmail())) {
                String verificationToken = generateVerificationToken(admin.getId());
                mailService.sendEmailHtml(request.getUser().getEmail(), request.getLink(), verificationToken);
            } else {
                throw new RuntimeException(userError);
            }
        } else {
            throw new RuntimeException(userError);
        }
    }

    @Override
    public boolean checkToken(String token) {
        return jwtTokenProvider.validateJwtToken(token);
    }

    @Override
    public void changePassword(ForgotPasswordRequest request) {
        String token = request.getToken();
        String id = decodeToken(token);

        if (id.matches(UserIdentifyPatternConstant.STUDENT_ID_PATTERN)) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));

            if (encoder.matches(request.getValues().getNewPassword(), student.getPassword())) {
                throw new RuntimeException(notBeSame);
            }
            if (!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())) {
                throw new RuntimeException(mustMatch);
            }
            student.setPassword(encoder.encode(request.getValues().getNewPassword()));
            studentRepository.saveAndFlush(student);
        } else if (id.matches(UserIdentifyPatternConstant.ADMIN_ID_PATTERN)) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, id)));

            if (encoder.matches(request.getValues().getNewPassword(), admin.getPassword())) {
                throw new RuntimeException(notBeSame);
            }
            if (!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())) {
                throw new RuntimeException(mustMatch);
            }
            admin.setPassword(encoder.encode(request.getValues().getNewPassword()));
            adminRepository.saveAndFlush(admin);
        } else {
            throw new TokenInvalid(ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.TOKEN_INVALID));
        }
    }

    private String generateVerificationToken(String id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTokenProvider.getMailTokenExpirationMs());

        return Jwts.builder()
                .setSubject(id)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtTokenProvider.getJwtSecret())
                .compact();
    }

    public String decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenProvider.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
