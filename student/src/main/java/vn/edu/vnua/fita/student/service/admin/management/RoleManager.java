package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.PermissionGroupConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.Permission;
import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.repository.jparepo.PermissionRepository;
import vn.edu.vnua.fita.student.repository.jparepo.RoleRepository;
import vn.edu.vnua.fita.student.service.iservice.IRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleManager implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final String roleNotFound = "Vai trò không tồn tại trong hệ thống";

    @Override
    public PermissionChecker checkPermissions(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException(roleNotFound));

        List<Permission> permissionList = permissionRepository.findAll();
        PermissionChecker response = new PermissionChecker();
        Map<String, Boolean> permissions;

        if (id.equals(RoleConstant.STUDENT)) {
            permissions = mapResponse(role, permissionList, PermissionGroupConstant.STUDENT);
        } else {
            permissions = mapResponse(role, permissionList, PermissionGroupConstant.ADMIN);
        }
        response.setCheckingPermissions(permissions);

        return response;
    }

    private Map<String, Boolean> mapResponse(Role role, List<Permission> permissionList, String group) {
        Map<String, Boolean> permissions = new HashMap<>();
        permissionList.forEach(
                permission -> {
                    if (permission.getType().equals(group)) {
                        permissions.put(permission.getId(), null);
                        for (Permission anotherPermission :
                                role.getPermissions()) {
                            if (Objects.equals(permission.getId(), anotherPermission.getId())) {
                                permissions.put(permission.getId(), true);
                            }
                        }
                    }
                });
        return permissions;
    }
}
