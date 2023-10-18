package vn.edu.vnua.fita.student.controller.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.StudentDTO;
import vn.edu.vnua.fita.student.dto.StudentStatusDTO;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;
import vn.edu.vnua.fita.student.service.admin.management.StudentStatusManager;
import vn.edu.vnua.fita.student.service.admin.statistic.StatisticService;
import vn.edu.vnua.fita.student.service.student.StudentServiceImpl;

import java.io.IOException;
import java.util.List;

@RequestMapping("student")
@RequiredArgsConstructor
@RestController
public class StudentController extends BaseController {
    private final StudentServiceImpl studentService;
    private final StudentStatusManager studentStatusManager;
    private final ModelMapper modelMapper;

    @PostMapping("avatar")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<?> updateMyAvatar(@RequestBody MultipartFile file) throws IOException {
        StudentDTO response = modelMapper.map(studentService.updateAvatar(file), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_STUDENT_PROFILE')")
    public ResponseEntity<?> updateMyProfile(@Valid @RequestBody UpdateStudentProfileRequest request) {
        StudentDTO response = modelMapper.map(studentService.updateProfile(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("change-password")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        StudentDTO response = modelMapper.map(studentService.changePassword(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("statistic")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<?> getStudentStatistic(){
        StudentStatistic response = studentService.getStatistic();
        return buildItemResponse(response);
    }

    @PostMapping("status")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public ResponseEntity<?> getStudentStatusList(){
        List<StudentStatusDTO> response = studentService.getStatus().stream().map(
                studentStatus -> modelMapper.map(studentStatus, StudentStatusDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }
}
