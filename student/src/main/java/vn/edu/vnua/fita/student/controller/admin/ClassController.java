package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.ClassDTO;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.admin.management.ClassManager;
import vn.edu.vnua.fita.student.entity.AClass;

import java.util.List;

@RestController
@RequestMapping("admin/class")
@RequiredArgsConstructor
public class ClassController extends BaseController {
    private final ClassManager classManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> getClassList(@Valid @RequestBody GetClassListRequest request){
        Page<AClass> page = classManager.getClassList(request);
        List<ClassDTO> response = page.getContent().stream().map(
                aClass -> modelMapper.map(aClass, ClassDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> createClass(@Valid @RequestBody CreateClassRequest request){
        ClassDTO response = modelMapper.map(classManager.createClass(request), ClassDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> createClass(@Valid @RequestBody UpdateClassRequest request){
        ClassDTO response = modelMapper.map(classManager.updateClass(request), ClassDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> deleteClass(@PathVariable String id){
        ClassDTO response = modelMapper.map(classManager.deleteClass(id), ClassDTO.class);
        return buildItemResponse(response);
    }
}
