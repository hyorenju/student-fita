package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.model.entity.Role;
import vn.edu.vnua.fita.student.request.admin.role.UpdateRoleRequest;

import java.util.List;

public interface IRoleService {
    Role updateRole(String id, UpdateRoleRequest request);
    PermissionChecker checkPermissions(String id);
}
