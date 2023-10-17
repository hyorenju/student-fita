package vn.edu.vnua.fita.student.service.visitor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.entity.Student;
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
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final String userError = "Tài khoải hoặc email không trùng khớp";
    private final String userNotFound = "Không tìm thấy mã sinh viên %s";

    @Override
    public void sendMessage(SendMailRequest request) {
        String studentId = request.getStudent().getId();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(userNotFound, studentId)));
        if (student.getEmail().equals(request.getStudent().getEmail())) {
            String verificationToken = generateVerificationToken(student.getId());
            mailService.sendEmailHtml(request.getStudent().getEmail(), request.getLink(), verificationToken);
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

        Student student = decodeToken(token);
        if (student != null) {
            if (encoder.matches(request.getValues().getNewPassword(), student.getPassword())) {
                throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
            }
            if (!Objects.equals(request.getValues().getNewPassword(), request.getValues().getConfirmPassword())) {
                throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
            }
            student.setPassword(encoder.encode(request.getValues().getNewPassword()));
            studentRepository.saveAndFlush(student);
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

    public Student decodeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtTokenProvider.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        String studentId = claims.getSubject();
        return studentRepository.findById(studentId).orElseThrow(
                () -> new RuntimeException(ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.TOKEN_INVALID))
        );
    }
}
