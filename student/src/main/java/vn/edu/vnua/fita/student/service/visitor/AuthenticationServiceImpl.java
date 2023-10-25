package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.common.UserIdentifyPatternConstant;
import vn.edu.vnua.fita.student.dto.ClassDTO;
import vn.edu.vnua.fita.student.dto.CourseDTO;
import vn.edu.vnua.fita.student.dto.MajorDTO;
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
import vn.edu.vnua.fita.student.response.StudentLoginResponse;
import vn.edu.vnua.fita.student.security.JwtTokenProvider;

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
    private final String cannotRefresh = "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại";

    @Value("${jwt.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Override
    public String authenticateUser(String id, String password) {
        if (id.matches(UserIdentifyPatternConstant.STUDENT_ID_PATTERN)) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(cannotLogin));

            if(!encoder.matches(password, student.getPassword())){
                throw new RuntimeException(cannotLogin);
            }

            StudentRefresher studentRefresher;
            if(studentRefresherRepository.existsByStudent(student)) {
                StudentRefresher token = studentRefresherRepository.findByStudent(student);
                if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                    studentRefresher = studentRefresherRepository.saveAndFlush(token);
                } else {
                    studentRefresher = token;
                }
            } else {
                studentRefresher =  studentRefresherRepository.saveAndFlush(jwtUtils.createStudentRefreshToken(student));
            }

            return studentRefresher.getToken();

//            Authentication authentication = authenticate(id, password);
//            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//            return new StudentLoginResponse(jwt,
//                    userDetails.getRoleId(),
//                    studentRefresher.getToken(),
//                    userDetails.getId(),
//                    userDetails.getSurname(),
//                    userDetails.getLastName(),
//                    userDetails.getAvatar(),
//                    userDetails.getCourse(),
//                    userDetails.getMajor(),
//                    userDetails.getAclass(),
//                    userDetails.getDob(),
//                    userDetails.getGender(),
//                    userDetails.getPhoneNumber(),
//                    userDetails.getEmail(),
//                    userDetails.getHomeTown(),
//                    userDetails.getResidence(),
//                    userDetails.getFatherName(),
//                    userDetails.getFatherPhoneNumber(),
//                    userDetails.getMotherName(),
//                    userDetails.getMotherPhoneNumber());
        } else if (id.matches(UserIdentifyPatternConstant.ADMIN_ID_PATTERN)) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(cannotLogin));

            if(!encoder.matches(password, admin.getPassword())){
                throw new RuntimeException(cannotLogin);
            }

            AdminRefresher adminRefresher;
            if(adminRefresherRepository.existsByAdmin(admin)) {
                AdminRefresher token = adminRefresherRepository.findByAdmin(admin);
                if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
                    adminRefresher = adminRefresherRepository.saveAndFlush(token);
                } else {
                    adminRefresher = token;
                }
            } else {
                adminRefresher =  adminRefresherRepository.saveAndFlush(jwtUtils.createAdminRefreshToken(admin));
            }

            return adminRefresher.getToken();

//            Authentication authentication = authenticate(id, password);
//            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);
//            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//            return new AdminLoginResponse(jwt,
//                    userDetails.getRoleId(),
//                    adminRefresher.getToken(),
//                    userDetails.getId(),
//                    userDetails.getName(),
//                    userDetails.getAvatar(),
//                    userDetails.getEmail());
        } else {
            throw new RuntimeException(cannotLogin);
        }
    }

    @Override
    public BaseLoginResponse verifyExpiration(String token) {
        StudentRefresher studentRefresher = studentRefresherRepository.findByToken(token);
        if(studentRefresher==null) {
            AdminRefresher adminRefresher = adminRefresherRepository.findByToken(token);
            if(adminRefresher.getExpiryDate().isBefore(Instant.now())) {
                adminRefresherRepository.delete(adminRefresher);
                throw new RuntimeException(cannotRefresh);
            }
            Admin admin = adminRepository.findById(adminRefresher.getAdmin().getId())
                    .orElseThrow(() -> new RuntimeException("Quản trị viên không tồn tại"));
            String accessToken = jwtUtils.generateTokenForAdmin(admin);


            return new AdminLoginResponse(accessToken,
                    admin.getRole().getId(),
                    adminRefresher.getToken(),
                    admin.getId(),
                    admin.getName(),
                    admin.getAvatar(),
                    admin.getEmail());
        } else {
            if (studentRefresher.getExpiryDate().isBefore(Instant.now())) {
                studentRefresherRepository.delete(studentRefresher);
                throw new RuntimeException(cannotRefresh);
            }
            Student student = studentRepository.findById(studentRefresher.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));
            String accessToken = jwtUtils.generateTokenForStudent(student);
            return new StudentLoginResponse(accessToken,
                    RoleConstant.STUDENT,
                    studentRefresher.getToken(),
                    student.getId(),
                    student.getSurname(),
                    student.getLastName(),
                    student.getAvatar(),
                    modelMapper.map(student.getCourse(), CourseDTO.class),
                    modelMapper.map(student.getMajor(), MajorDTO.class),
                    modelMapper.map(student.getAclass(), ClassDTO.class),
                    student.getDob(),
                    student.getGender(),
                    student.getPhoneNumber(),
                    student.getEmail(),
                    student.getHomeTown(),
                    student.getResidence(),
                    student.getFatherName(),
                    student.getFatherPhoneNumber(),
                    student.getMotherName(),
                    student.getMotherPhoneNumber());
        }
    }

    private Authentication authenticate(String id, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
