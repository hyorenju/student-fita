package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.request.admin.studentstatus.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.ExportStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.UpdateStudentStatusRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IStudentStatusService {
    Page<StudentStatus> getStudentStatusList(GetStudentStatusListRequest request);
    StudentStatus createStudentStatus(CreateStudentStatusRequest request) throws ParseException;
    StudentStatus updateStudentStatus(UpdateStudentStatusRequest request, Long id) throws ParseException;
    StudentStatus deleteStudentStatus(Long id);
    List<StudentStatus> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String exportToExcel(ExportStudentStatusRequest request);
}
