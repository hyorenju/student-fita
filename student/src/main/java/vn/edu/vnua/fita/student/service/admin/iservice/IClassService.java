package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.ExportClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.ExportStudentStatusRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IClassService {
    Page<AClass> getClassList(GetClassListRequest request);
    List<AClass> getAllClass();
    AClass createClass(CreateClassRequest request);
    AClass updateClass(UpdateClassRequest request);
    AClass deleteClass(String id);
    List<AClass> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String exportToExcel(ExportClassListRequest request);
}
