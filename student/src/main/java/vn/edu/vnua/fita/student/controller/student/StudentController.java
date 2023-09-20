package vn.edu.vnua.fita.student.controller.student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.PointDTO;
import vn.edu.vnua.fita.student.entity.dto.StudentDTO;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;
import vn.edu.vnua.fita.student.service.student.StudentServiceImpl;

import java.io.IOException;
import java.util.List;

@RequestMapping("student")
@RequiredArgsConstructor
@RestController
public class StudentController extends BaseController {
    private final StudentServiceImpl studentService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> getMyProfile() {
        StudentDTO response = modelMapper.map(studentService.getStudent(), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("avatar")
    public ResponseEntity<?> updateMyAvatar(@RequestBody MultipartFile file) throws IOException {
        StudentDTO response = modelMapper.map(studentService.updateAvatar(file), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateMyProfile(@Valid @RequestBody UpdateStudentProfileRequest request) {
        StudentDTO response = modelMapper.map(studentService.updateProfile(request), StudentDTO.class);
        return buildItemResponse(response);
    }
}
