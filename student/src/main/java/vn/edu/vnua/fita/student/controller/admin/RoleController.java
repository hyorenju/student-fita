package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.model.dto.RoleDTO;
import vn.edu.vnua.fita.student.request.admin.role.UpdateRoleRequest;
import vn.edu.vnua.fita.student.service.admin.management.RoleManager;

import java.util.List;

@RestController
@RequestMapping("admin/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    private final RoleManager roleManager;
    private final ModelMapper modelMapper;

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody UpdateRoleRequest request) {
        RoleDTO response = modelMapper.map(roleManager.updateRole(id, request), RoleDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> getCheckingPermissions(@PathVariable String id) {
        List<PermissionChecker> response =  roleManager.checkPermissions(id);
        return buildListItemResponse(response, response.size());
    }
}