package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.RoleConstant;
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

    @Override
    public BaseLoginResponse authenticateUser(String id, String password) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        Optional<Admin> adminOptional = adminRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if(!encoder.matches(password, student.getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }

            StudentRefresher studentRefresher;
            if(studentRefresherRepository.existsByStudent(student)) {
                StudentRefresher token = studentRefresherRepository.findByStudent(student);
                if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    studentRefresher = studentRefresherRepository.saveAndFlush(jwtUtils.createStudentRefreshToken(id));
                } else {
                    studentRefresher = token;
                }
            } else {
                studentRefresher =  studentRefresherRepository.saveAndFlush(jwtUtils.createStudentRefreshToken(id));
            }

            Authentication authentication = authenticate(id, password);

            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new StudentLoginResponse(jwt,
                    userDetails.getRoleId(),
                    studentRefresher.getToken(),
                    userDetails.getId(),
                    userDetails.getSurname(),
                    userDetails.getLastName(),
                    userDetails.getAvatar(),
                    userDetails.getCourse(),
                    userDetails.getMajor(),
                    userDetails.getAclass(),
                    userDetails.getDob(),
                    userDetails.getGender(),
                    userDetails.getPhoneNumber(),
                    userDetails.getEmail(),
                    userDetails.getHomeTown(),
                    userDetails.getResidence(),
                    userDetails.getFatherName(),
                    userDetails.getFatherPhoneNumber(),
                    userDetails.getMotherName(),
                    userDetails.getMotherPhoneNumber());
        } else if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if(!encoder.matches(password, admin.getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }

            AdminRefresher adminRefresher;
            if(adminRefresherRepository.existsByAdmin(admin)) {
                AdminRefresher token = adminRefresherRepository.findByAdmin(admin);
                if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    adminRefresher = adminRefresherRepository.saveAndFlush(jwtUtils.createAdminRefreshToken(id));
                } else {
                    adminRefresher = token;
                }
            } else {
                adminRefresher =  adminRefresherRepository.saveAndFlush(jwtUtils.createAdminRefreshToken(id));
            }

            Authentication authentication = authenticate(id, password);

            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new AdminLoginResponse(jwt,
                    userDetails.getRoleId(),
                    adminRefresher.getToken(),
                    userDetails.getId(),
                    userDetails.getName(),
                    userDetails.getAvatar(),
                    userDetails.getEmail());
        } else {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    @Override
    public BaseLoginResponse verifyExpiration(String token) {
        StudentRefresher studentRefresher = studentRefresherRepository.findByToken(token);
        if(studentRefresher==null){
            AdminRefresher adminRefresher = adminRefresherRepository.findByToken(token);
            if(adminRefresher.getExpiryDate().compareTo(Instant.now()) < 0) {
                adminRefresherRepository.delete(adminRefresher);
                throw new RuntimeException("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
            }
            Admin admin = adminRepository.findById(adminRefresher.getAdmin().getId())
                    .orElseThrow(() -> new RuntimeException("Quản trị viên không tồn tại."));
            String accessToken = jwtUtils.generateToken(admin.getId());
            return new AdminLoginResponse(accessToken,
                    admin.getRole().getId(),
                    adminRefresher.getToken(),
                    admin.getId(),
                    admin.getName(),
                    admin.getAvatar(),
                    admin.getEmail());
        }
        if(studentRefresher.getExpiryDate().compareTo(Instant.now()) < 0) {
            studentRefresherRepository.delete(studentRefresher);
            throw new RuntimeException("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
        }
        Student student = studentRepository.findById(studentRefresher.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại."));
        String accessToken = jwtUtils.generateToken(student.getId());
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

    private Authentication authenticate(String id, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
