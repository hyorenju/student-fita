package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.dto.ClassDTO;
import vn.edu.vnua.fita.student.dto.CourseDTO;
import vn.edu.vnua.fita.student.dto.MajorDTO;
import vn.edu.vnua.fita.student.model.authentication.UserDetailsImpl;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if(id.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy quản trị viên"));
            return UserDetailsImpl.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .email(admin.getEmail())
                    .password(admin.getPassword())
                    .avatar(admin.getAvatar())
                    .roleId(admin.getRole().getId())
                    .authorities(admin.getAuthorities())
                    .build();
        } else {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
            return UserDetailsImpl.builder()
                    .id(student.getId())
                    .surname(student.getSurname())
                    .lastName(student.getLastName())
                    .avatar(student.getAvatar())
                    .course(modelMapper.map(student.getCourse(), CourseDTO.class))
                    .major(modelMapper.map(student.getMajor(), MajorDTO.class))
                    .aclass(modelMapper.map(student.getAclass(), ClassDTO.class))
                    .dob(student.getDob())
                    .gender(student.getGender())
                    .phoneNumber(student.getPhoneNumber())
                    .email(student.getEmail())
                    .homeTown(student.getHomeTown())
                    .residence(student.getResidence())
                    .fatherName(student.getFatherName())
                    .fatherPhoneNumber(student.getFatherPhoneNumber())
                    .motherName(student.getMotherName())
                    .motherPhoneNumber(student.getMotherPhoneNumber())
                    .password(student.getPassword())
                    .roleId(student.getRole().getId())
                    .authorities(student.getAuthorities())
                    .build();
        }
    }
}
