package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.dto.MajorDTO;
import vn.edu.vnua.fita.student.model.entity.Major;
import vn.edu.vnua.fita.student.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.fita.student.request.admin.major.GetMajorListRequest;
import vn.edu.vnua.fita.student.request.admin.major.UpdateMajorRequest;
import vn.edu.vnua.fita.student.service.admin.management.MajorManager;

import java.util.List;

@RequestMapping("admin/major")
@RequiredArgsConstructor
@RestController
public class MajorController extends BaseController {
    private final MajorManager majorManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getMajorList(@Valid @RequestBody GetMajorListRequest request){
        Page<Major> page = majorManager.getMajorList(request);
        List<MajorDTO> response = page.getContent().stream().map(
                major -> modelMapper.map(major, MajorDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createMajor(@Valid @RequestBody CreateMajorRequest request){
        MajorDTO response = modelMapper.map(majorManager.createMajor(request), MajorDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> createMajor(@Valid @RequestBody UpdateMajorRequest request){
        MajorDTO response = modelMapper.map(majorManager.updateMajor(request), MajorDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable String id){
        MajorDTO response = modelMapper.map(majorManager.deleteMajor(id), MajorDTO.class);
        return buildItemResponse(response);
    }
}
