package vn.edu.vnua.fita.student.service.student;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.repository.jparepo.PointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.admin.iservice.IStudentService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final FirebaseService firebaseService;
    private final String studentNotFound = "Không tìm thấy sinh viên";


    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Student getStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));
    }

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
    public List<Point> getPoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentId = authentication.getPrincipal().toString();
        return pointRepository.findAllByStudentId(studentId);
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
}
