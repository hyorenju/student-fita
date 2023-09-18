package vn.edu.vnua.fita.student.controller.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.service.admin.management.RoleManager;

@RestController
@RequestMapping("admin/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {
    private final RoleManager roleManager;

    @PostMapping("{id}")
    public ResponseEntity<?> getCheckingPermissions(@PathVariable String id) {
        PermissionChecker response =  roleManager.checkPermissions(id);
        return buildItemResponse(response);
    }
}