package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.authentication.UserDetailsImpl;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.response.BaseLoginResponse;
import vn.edu.vnua.fita.student.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtUtils;
    private final PasswordEncoder encoder;

    @Override
    public BaseLoginResponse authenticateUser(String id, String password) {
        if (studentRepository.existsById(id)) {
            if(!encoder.matches(password, studentRepository.findById(id).get().getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }
        } else if (adminRepository.existsById(id)) {
            if(!encoder.matches(password, adminRepository.findById(id).get().getPassword())){
                throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
            }
        } else {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateTokenWithAuthorities(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new BaseLoginResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getAvatar(),
                userDetails.getCourse(),
                userDetails.getMajor(),
                userDetails.getAClass(),
                userDetails.getDob(),
                userDetails.getGender(),
                userDetails.getPhoneNumber(),
                userDetails.getEmail(),
                userDetails.getHomeTown(),
                userDetails.getResidence(),
                userDetails.getFatherName(),
                userDetails.getFatherPhoneNumber(),
                userDetails.getMotherName(),
                userDetails.getMotherPhoneNumber(),
                userDetails.getRoleId());
    }
}
