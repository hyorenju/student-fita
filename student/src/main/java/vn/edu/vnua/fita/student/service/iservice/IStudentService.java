package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.TrashStudent;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.request.admin.student.*;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IStudentService {
    Page<Student> getStudentList(GetStudentListRequest request);
    Student getStudentById(String id);
    Student createStudent(CreateStudentRequest request) throws ParseException;
    Student updateStudent(UpdateStudentRequest request) throws ParseException;
    TrashStudent deleteStudent(String id);
    List<TrashStudent> deleteManyStudent(DeleteStudentRequest request);
    TrashStudent restoreStudent(Long id);
    List<TrashStudent> restoreManyStudent(RestoreStudentRequest request);
    Page<TrashStudent> getTrashStudentList(GetTrashStudentRequest request);
    Student updateAvatar(MultipartFile file, String id) throws IOException;
    void importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String exportToExcel(ExportStudentListRequest request) throws IOException;
}
