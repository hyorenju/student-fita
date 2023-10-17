package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.request.admin.role.UpdateRoleRequest;

import java.util.List;

public interface IRoleService {
    Role updateRole(String id, UpdateRoleRequest request);
    List<PermissionChecker> checkPermissions(String id);
}
