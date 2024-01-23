package vn.edu.vnua.fita.student.service.student;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.student.UpdateEmailRequest;
import vn.edu.vnua.fita.student.request.student.UpdatePhoneRequest;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    Student getProfile();
    Student updateAvatar(MultipartFile file) throws IOException;
    Student updateProfile(UpdateStudentProfileRequest request);
    Student changePassword(ChangePasswordRequest request);
    StudentStatistic getStatistic();
    List<StudentStatus> getStatus();
    Student updateEmail(UpdateEmailRequest request);
    Student updatePhoneNumber2(UpdatePhoneRequest request);
}
