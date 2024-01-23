package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.TrashStudentDTO;
import vn.edu.vnua.fita.student.dto.StudentDTO;
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
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("admin/student")
@RequiredArgsConstructor
public class StudentManagerController extends BaseController {
    private final StudentManager studentManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_STUDENT_LIST', 'SUPERADMIN')")
    public ResponseEntity<?> getStudentList(@Valid @RequestBody GetStudentListRequest request) {
        Page<Student> page = studentManager.getStudentList(request);
        List<StudentDTO> response = page.getContent().stream().map(
                student -> modelMapper.map(student, StudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

//    @PostMapping("{id}")
//    public ResponseEntity<?> getStudentDetail(@PathVariable String id) {
//        StudentDTO response = modelMapper.map(studentManager.getStudentById(id), StudentDTO.class);
//        return buildItemResponse(response);
//    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> createStudent(@Valid @RequestBody CreateStudentRequest request) throws ParseException {
        StudentDTO response = modelMapper.map(studentManager.createStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('UPDATE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> updateStudent(@Valid @RequestBody UpdateStudentRequest request) throws ParseException {
        StudentDTO response = modelMapper.map(studentManager.updateStudent(request), StudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        TrashStudentDTO response = modelMapper.map(studentManager.deleteStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteManyStudent(@RequestBody @Valid DeleteStudentRequest request) {
        List<TrashStudentDTO> response = studentManager.deleteManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("delete-permanently/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteStudentPermanently(@PathVariable Long id) {
        TrashStudentDTO response = modelMapper.map(studentManager.deletePermanent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore/{id}")
    @PreAuthorize("hasAnyAuthority('RESTORE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> restoreStudent(@PathVariable Long id) {
        TrashStudentDTO response = modelMapper.map(studentManager.restoreStudent(id), TrashStudentDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    @PreAuthorize("hasAnyAuthority('RESTORE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> restoreManyStudent(@RequestBody @Valid RestoreStudentRequest request) {
        List<TrashStudentDTO> response = studentManager.restoreManyStudent(request).stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    @PreAuthorize("hasAnyAuthority('RESTORE_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> getTrashStudent(@Valid @RequestBody GetTrashStudentRequest request) {
        Page<TrashStudent> page = studentManager.getTrashStudentList(request);
        List<TrashStudentDTO> response = page.getContent().stream().map(
                trashStudent -> modelMapper.map(trashStudent, TrashStudentDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> importStudentList(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<StudentDTO> response = studentManager.importFromExcel(file).stream().map(
                student -> modelMapper.map(student, StudentDTO.class)
        ).toList();
        return buildItemResponse(response);
    }

    @PostMapping("export")
    @PreAuthorize("hasAnyAuthority('EXPORT_STUDENT', 'SUPERADMIN')")
    public ResponseEntity<?> exportStudentList(@RequestBody ExportStudentListRequest request){
        String response = studentManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
