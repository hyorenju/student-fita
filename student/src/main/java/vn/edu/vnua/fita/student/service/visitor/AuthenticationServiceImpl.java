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
import vn.edu.vnua.fita.student.model.dto.ClassDTO;
import vn.edu.vnua.fita.student.model.dto.CourseDTO;
import vn.edu.vnua.fita.student.model.dto.MajorDTO;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.RefreshToken;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.model.authentication.UserDetailsImpl;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.RefreshTokenRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.response.AdminLoginResponse;
import vn.edu.vnua.fita.student.response.BaseLoginResponse;
import vn.edu.vnua.fita.student.response.StudentLoginResponse;
import vn.edu.vnua.fita.student.security.JwtTokenProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
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

            RefreshToken refreshToken;
            if(refreshTokenRepository.existsByStudent(student)) {
                RefreshToken token = refreshTokenRepository.findByStudent(student);
                if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
                    refreshToken = refreshTokenRepository.saveAndFlush(jwtUtils.createRefreshToken(id));
                } else {
                    refreshToken = token;
                }
            } else {
                refreshToken =  refreshTokenRepository.saveAndFlush(jwtUtils.createRefreshToken(id));
            }

            Authentication authentication = authenticate(id, password);

            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new StudentLoginResponse(jwt,
                    userDetails.getRoleId(),
                    refreshToken.getToken(),
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

            Authentication authentication = authenticate(id, password);

            String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new AdminLoginResponse(jwt,
                    userDetails.getRoleId(),
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
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
        }
        Student student = studentRepository.findById(refreshToken.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại."));
        String accessToken = jwtUtils.generateToken(student.getId());
        return new StudentLoginResponse(accessToken,
                RoleConstant.STUDENT,
                refreshToken.getToken(),
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
