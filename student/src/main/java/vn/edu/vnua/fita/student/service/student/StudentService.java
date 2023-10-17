package vn.edu.vnua.fita.student.service.student;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;

import java.io.IOException;

public interface StudentService {
    Student updateAvatar(MultipartFile file) throws IOException;
    Student updateProfile(UpdateStudentProfileRequest request);
    Student changePassword(ChangePasswordRequest request);
}
