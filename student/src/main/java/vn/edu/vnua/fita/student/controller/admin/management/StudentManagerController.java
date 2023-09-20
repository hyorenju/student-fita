package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.TrashStudentDTO;
import vn.edu.vnua.fita.student.entity.dto.StudentDTO;
import vn.edu.vnua.fita.student.entity.TrashStudent;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.request.admin.student.*;
import vn.edu.vnua.fita.student.service.admin.management.StudentManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("admin/student")
@RequiredArgsConstructor
public class StudentManagerController extends BaseController {
    private final StudentManager studentManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getStudentList(@Valid @RequestBody GetStudentListRequest request) {
        Page<Student> page = studentManager.getStudentList(request);
        List<StudentDTO> response = page.getContent().stream().map(
                student -> modelMapper.map(student, StudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> getStudentDetail(@PathVariable String id) {
        StudentDTO response = modelMapper.map(studentManager.getStudentById(id), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        StudentDTO response = modelMapper.map(studentManager.createStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateStudent(@Valid @RequestBody UpdateStudentRequest request) {
        StudentDTO response = modelMapper.map(studentManager.updateStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        TrashStudentDTO response = modelMapper.map(studentManager.deleteStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteManyStudent(@RequestBody @Valid DeleteStudentRequest request) {
        List<TrashStudentDTO> response = studentManager.deleteManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("restore/{id}")
    public ResponseEntity<?> restoreStudent(@PathVariable Long id) {
        TrashStudentDTO response = modelMapper.map(studentManager.restoreStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    public ResponseEntity<?> restoreManyStudent(@RequestBody @Valid RestoreStudentRequest request) {
        List<TrashStudentDTO> response = studentManager.restoreManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    public ResponseEntity<?> getTrashStudent(@Valid @RequestBody GetTrashStudentRequest request) {
        Page<TrashStudent> page = studentManager.getTrashStudentList(request);
        List<TrashStudentDTO> response = page.getContent().stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("import")
    public ResponseEntity<?> importStudentList(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        studentManager.importFromExcel(file);
        String response = "Nhập liệu thành công";
        return buildItemResponse(response);
    }

    @PostMapping("export")
    public ResponseEntity<?> exportStudentList(@RequestBody ExportStudentListRequest request){
        String response = studentManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
