package vn.edu.vnua.fita.student.service.iservice;

import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;

public interface IRoleService {
    PermissionChecker checkPermissions(String id);
}
