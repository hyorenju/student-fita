package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.PointDTO;
import vn.edu.vnua.fita.student.dto.StudentStatusDTO;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.request.admin.point.ExportPointListRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.ExportStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.UpdateStudentStatusRequest;
import vn.edu.vnua.fita.student.service.admin.management.StudentStatusManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("admin/student-status")
@RequiredArgsConstructor
public class StudentStatusController extends BaseController {
    private final StudentStatusManager studentStatusManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_STATUS_LIST', 'SUPERADMIN', 'MOD_GET_STUDENT_STATUS_LIST')")
    public ResponseEntity<?> getStudentStatusList(@Valid @RequestBody GetStudentStatusListRequest request){
        Page<StudentStatus> page = studentStatusManager.getStudentStatusList(request);
        List<StudentStatusDTO> response = page.getContent().stream().map(
                studentStatus -> modelMapper.map(studentStatus, StudentStatusDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_STUDENT_STATUS', 'SUPERADMIN')")
    public ResponseEntity<?> createStudentStatus(@Valid @RequestBody CreateStudentStatusRequest request) throws ParseException {
        StudentStatusDTO response = modelMapper.map(studentStatusManager.createStudentStatus(request), StudentStatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_STUDENT_STATUS', 'SUPERADMIN')")
    public ResponseEntity<?> updateStudentStatus(@Valid @RequestBody UpdateStudentStatusRequest request, @PathVariable Long id) throws ParseException {
        StudentStatusDTO response = modelMapper.map(studentStatusManager.updateStudentStatus(request, id), StudentStatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT_STATUS', 'SUPERADMIN')")
    public ResponseEntity<?> deleteStudentStatus(@PathVariable Long id){
        StudentStatusDTO response = modelMapper.map(studentStatusManager.deleteStudentStatus(id), StudentStatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('IMPORT_STUDENT_STATUS', 'SUPERADMIN')")
    public ResponseEntity<?> importPointList(@RequestBody MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<StudentStatusDTO> response = studentStatusManager.importFromExcel(file).stream().map(
                studentStatus -> modelMapper.map(studentStatus, StudentStatusDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("export")
    @PreAuthorize("hasAnyAuthority('EXPORT_STUDENT_STATUS', 'SUPERADMIN', 'MOD_EXPORT_STUDENT_STATUS')")
    public ResponseEntity<?> exportPointList(@RequestBody ExportStudentStatusRequest request){
        String response = studentStatusManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
