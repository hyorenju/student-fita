package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.StudentStatusDTO;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.request.admin.student_status.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.UpdateStudentStatusRequest;
import vn.edu.vnua.fita.student.service.admin.management.StudentStatusManager;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("admin/student-status")
@RequiredArgsConstructor
public class StudentStatusController extends BaseController {
    private final StudentStatusManager studentStatusManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getStudentStatusList(@Valid @RequestBody GetStudentStatusListRequest request){
        Page<StudentStatus> page = studentStatusManager.getStudentStatusList(request);
        List<StudentStatusDTO> response = page.getContent().stream().map(
                studentStatus -> modelMapper.map(studentStatus, StudentStatusDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createStudentStatus(@Valid @RequestBody CreateStudentStatusRequest request) throws ParseException {
        StudentStatusDTO response = modelMapper.map(studentStatusManager.createStudentStatus(request), StudentStatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updateStudentStatus(@Valid @RequestBody UpdateStudentStatusRequest request, @PathVariable Long id) throws ParseException {
        StudentStatusDTO response = modelMapper.map(studentStatusManager.updateStudentStatus(request, id), StudentStatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteStudentStatus(@PathVariable Long id){
        StudentStatusDTO response = modelMapper.map(studentStatusManager.deleteStudentStatus(id), StudentStatusDTO.class);
        return buildItemResponse(response);
    }
}
