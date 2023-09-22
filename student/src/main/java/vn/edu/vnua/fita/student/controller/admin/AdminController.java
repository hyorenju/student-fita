package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.dto.AdminDTO;
import vn.edu.vnua.fita.student.model.dto.TrashAdminDTO;
import vn.edu.vnua.fita.student.model.entity.TrashAdmin;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.request.admin.admin.*;
import vn.edu.vnua.fita.student.service.admin.management.AdminManager;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("admin/admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {
    private final AdminManager adminManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getAdminList(@Valid @RequestBody GetAdminListRequest request){
        Page<Admin> page = adminManager.getAdminList(request);
        List<AdminDTO> response = page.getContent().stream().map(
                admin -> modelMapper.map(admin, AdminDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody CreateAdminRequest request){
        AdminDTO response = modelMapper.map(adminManager.createAdmin(request), AdminDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateAdmin(@Valid @RequestBody UpdateAdminRequest request){
        AdminDTO response = modelMapper.map(adminManager.updateAdmin(request), AdminDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("edit")
    public ResponseEntity<?> editProfile(@Valid @RequestBody UpdateProfileRequest request){
        AdminDTO response = modelMapper.map(adminManager.updateProfile(request), AdminDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String id){
        TrashAdminDTO response = modelMapper.map(adminManager.deleteAdmin(id), TrashAdminDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore/{id}")
    public ResponseEntity<?> restoreAdmin(@PathVariable Long id){
        TrashAdminDTO response = modelMapper.map(adminManager.restoreAdmin(id), TrashAdminDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("trash")
    public ResponseEntity<?> getTrashAdmin(@Valid @RequestBody GetTrashAdminRequest request){
        Page<TrashAdmin> page = adminManager.getTrashAdminList(request);
        List<TrashAdminDTO> response = page.getContent().stream().map(
                trashAdmin -> modelMapper.map(trashAdmin, TrashAdminDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody MultipartFile file) throws IOException {
        AdminDTO response = modelMapper.map(adminManager.updateAvatar(file), AdminDTO.class);
        return buildItemResponse(response);
    }
}
