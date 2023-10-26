package vn.edu.vnua.fita.student.service.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.common.UserIdentifyPatternConstant;
import vn.edu.vnua.fita.student.domain.exception.TokenExpired;
import vn.edu.vnua.fita.student.domain.exception.TokenInvalid;
import vn.edu.vnua.fita.student.dto.*;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.AdminRefresher;
import vn.edu.vnua.fita.student.entity.StudentRefresher;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.authentication.UserDetailsImpl;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRefresherRepository;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRefresherRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.response.AdminLoginResponse;
import vn.edu.vnua.fita.student.response.BaseLoginResponse;
import vn.edu.vnua.fita.student.response.BaseResponse;
import vn.edu.vnua.fita.student.response.StudentLoginResponse;
import vn.edu.vnua.fita.student.security.JwtTokenProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentRefresherRepository studentRefresherRepository;
    private final AdminRefresherRepository adminRefresherRepository;
    private final JwtTokenProvider jwtUtils;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final String cannotLogin = "Tài khoản hoặc mật khẩu không chính xác";

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public BaseLoginResponse authenticateUser(String id, String password) {
        if (id.matches(UserIdentifyPatternConstant.STUDENT_ID_PATTERN)) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(cannotLogin));

            if (!encoder.matches(password, student.getPassword())) {
                throw new RuntimeException(cannotLogin);
            }

            StudentRefresher studentRefresher;
            if (studentRefresherRepository.existsByStudent(student)) {
                StudentRefresher token = studentRefresherRepository.findByStudent(student);
                if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                    studentRefresher = studentRefresherRepository.saveAndFlush(token);
                } else {
                    studentRefresher = token;
                }
            } else {
                studentRefresher = studentRefresherRepository.saveAndFlush(jwtUtils.createStudentRefreshToken(student));
            }

            Authentication authentication = authenticate(id, password);
            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            StudentDTO studentDTO = modelMapper.map(userDetails, StudentDTO.class);

            return new StudentLoginResponse(jwt,
                    userDetails.getRoleId(),
                    studentRefresher.getToken(),
                    studentDTO.getId(),
                    studentDTO.getSurname(),
                    studentDTO.getLastName(),
                    studentDTO.getAvatar(),
                    studentDTO.getCourse(),
                    studentDTO.getMajor(),
                    studentDTO.getAclass(),
                    studentDTO.getDob(),
                    studentDTO.getGender(),
                    studentDTO.getPhoneNumber(),
                    studentDTO.getEmail(),
                    studentDTO.getHomeTown(),
                    studentDTO.getResidence(),
                    studentDTO.getFatherName(),
                    studentDTO.getFatherPhoneNumber(),
                    studentDTO.getMotherName(),
                    studentDTO.getMotherPhoneNumber());
        } else if (id.matches(UserIdentifyPatternConstant.ADMIN_ID_PATTERN)) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(cannotLogin));

            if (!encoder.matches(password, admin.getPassword())) {
                throw new RuntimeException(cannotLogin);
            }

            AdminRefresher adminRefresher;
            if (adminRefresherRepository.existsByAdmin(admin)) {
                AdminRefresher token = adminRefresherRepository.findByAdmin(admin);
                if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                    adminRefresher = adminRefresherRepository.saveAndFlush(token);
                } else {
                    adminRefresher = token;
                }
            } else {
                adminRefresher = adminRefresherRepository.saveAndFlush(jwtUtils.createAdminRefreshToken(admin));
            }

//            return adminRefresher.getToken();

            Authentication authentication = authenticate(id, password);
            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            AdminDTO adminDTO = modelMapper.map(userDetails, AdminDTO.class);

            return new AdminLoginResponse(jwt,
                    userDetails.getRoleId(),
                    adminRefresher.getToken(),
                    adminDTO.getId(),
                    adminDTO.getName(),
                    adminDTO.getAvatar(),
                    adminDTO.getEmail());
        } else {
            throw new RuntimeException(cannotLogin);
        }
    }

    @Override
    public BaseLoginResponse verifyExpiration(String token) throws IOException {
        StudentRefresher studentRefresher = studentRefresherRepository.findByToken(token);
        if (studentRefresher == null) {
            AdminRefresher adminRefresher = adminRefresherRepository.findByToken(token);
            if (adminRefresher.getExpiryDate().isBefore(Instant.now())) {
//                adminRefresherRepository.delete(adminRefresher);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setFailed(ErrorCodeDefinitions.REFRESH_EXPIRED, ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.REFRESH_EXPIRED));
                return null;
//                throw new TokenExpired(ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.REFRESH_EXPIRED));
            }
            Admin admin = adminRepository.findById(adminRefresher.getAdmin().getId())
                    .orElseThrow(() -> new RuntimeException("Quản trị viên không tồn tại"));
            String jwt = jwtUtils.generateTokenForAdmin(admin);
            AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);

            return new AdminLoginResponse(jwt,
                    admin.getRole().getId(),
                    adminRefresher.getToken(),
                    adminDTO.getId(),
                    adminDTO.getName(),
                    adminDTO.getAvatar(),
                    adminDTO.getEmail());
        } else {
            if (studentRefresher.getExpiryDate().isBefore(Instant.now())) {
//                studentRefresherRepository.delete(studentRefresher);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setFailed(ErrorCodeDefinitions.REFRESH_EXPIRED, ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.REFRESH_EXPIRED));
                return null;
            }
            Student student = studentRepository.findById(studentRefresher.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));
            String jwt = jwtUtils.generateTokenForStudent(student);
            StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

            return new StudentLoginResponse(jwt,
                    student.getRole().getId(),
                    studentRefresher.getToken(),
                    studentDTO.getId(),
                    studentDTO.getSurname(),
                    studentDTO.getLastName(),
                    studentDTO.getAvatar(),
                    studentDTO.getCourse(),
                    studentDTO.getMajor(),
                    studentDTO.getAclass(),
                    studentDTO.getDob(),
                    studentDTO.getGender(),
                    studentDTO.getPhoneNumber(),
                    studentDTO.getEmail(),
                    studentDTO.getHomeTown(),
                    studentDTO.getResidence(),
                    studentDTO.getFatherName(),
                    studentDTO.getFatherPhoneNumber(),
                    studentDTO.getMotherName(),
                    studentDTO.getMotherPhoneNumber());
        }
    }

    private Authentication authenticate(String id, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
