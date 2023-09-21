package vn.edu.vnua.fita.student.service.student;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.model.entity.Point;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    Student getStudent();
    Student updateAvatar(MultipartFile file) throws IOException;
    List<Point> getPoint();
    Student updateProfile(UpdateStudentProfileRequest request);
}
