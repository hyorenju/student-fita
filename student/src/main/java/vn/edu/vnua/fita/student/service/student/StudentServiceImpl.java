package vn.edu.vnua.fita.student.service.student;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.Point;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.PointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FirebaseService firebaseService;
    private final PasswordEncoder encoder;

    private final String studentNotFound = "Không tìm thấy sinh viên";


    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Student updateAvatar(MultipartFile file) throws IOException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        Blob blob = firebaseService.uploadImage(file, bucketName);

        student.setAvatar(blob
                .signUrl(FirebaseExpirationTimeConstant.EXPIRATION_TIME, TimeUnit.MILLISECONDS)
                .toString());

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student updateProfile(UpdateStudentProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student changePassword(ChangePasswordRequest request) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        if(!encoder.matches(request.getCurrentPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if(encoder.matches(request.getNewPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        if(!Objects.equals(request.getNewPassword(), request.getConfirmPassword())){
            throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
        }

        student.setPassword(encoder.encode(request.getNewPassword()));
        return studentRepository.saveAndFlush(student);
    }
}
