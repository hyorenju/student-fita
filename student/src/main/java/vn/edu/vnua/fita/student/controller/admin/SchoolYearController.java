package vn.edu.vnua.fita.student.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.SchoolYearDTO;
import vn.edu.vnua.fita.student.service.admin.management.SchoolYearManager;

import java.util.List;

@RestController
@RequestMapping("admin/school-year")
@RequiredArgsConstructor
public class SchoolYearController extends BaseController {
    private final ModelMapper modelMapper;
    private final SchoolYearManager schoolYearManager;

    @PostMapping("selection")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN', 'MOD')")
    public ResponseEntity<?> getAllSchoolYear(){
        List<SchoolYearDTO> response = schoolYearManager.getAllSchoolYear().stream().map(
                schoolYear -> modelMapper.map(schoolYear, SchoolYearDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }
}
