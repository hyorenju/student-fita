package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.Permission;
import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.repository.jparepo.PermissionRepository;
import vn.edu.vnua.fita.student.repository.jparepo.RoleRepository;
import vn.edu.vnua.fita.student.request.admin.role.UpdateRoleRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IRoleService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleManager implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final String roleNotFound = "Vai trò không tồn tại trong hệ thống";

    @Override
    public Role updateRole(String id, UpdateRoleRequest request) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException(roleNotFound);
        }

        checkPermissionIsValid(request.getPermissionIds());
        validateRoleExist(id);
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            List<Permission> permissions = buildPermission(request.getPermissionIds());
            Role roleUpdatePojo = role.get();
            roleUpdatePojo.setPermissions(permissions);
            return roleRepository.save(roleUpdatePojo);
        }
        throw new RuntimeException("Có lỗi xảy ra trong quá trình update vai trò");
    }

    @Override
    public List<PermissionChecker> checkPermissions(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException(roleNotFound));
        if (id.equals(RoleConstant.STUDENT)) {
            return getPermissionChecker(role, permissionRepository.findAllByType(RoleConstant.STUDENT));
        } else if (id.equals(RoleConstant.MOD)) {
            return getPermissionChecker(role, permissionRepository.findAllByType(RoleConstant.MOD));
        } else if (id.equals(RoleConstant.ADMIN)) {
            return getPermissionChecker(role, permissionRepository.findAllByType(RoleConstant.ADMIN));
        } else {
            return getPermissionChecker(role, permissionRepository.findAllByType(RoleConstant.SUPERADMIN));
        }
    }

    private List<PermissionChecker> getPermissionChecker(Role role, List<Permission> permissionList) {
        List<PermissionChecker> permissions = new ArrayList<>();
        permissionList.forEach(
                permission -> {
                    if (permission.getType().equals(role.getId())) {
                        PermissionChecker permissionChecker = new PermissionChecker();
                        permissionChecker.setId(permission.getId());
                        permissionChecker.setName(permission.getName());
                        for (Permission anotherPermission :
                                role.getPermissions()) {
                            if (Objects.equals(permission.getId(), anotherPermission.getId())) {
                                permissionChecker.setIsAllowed(true);
                            }
                        }
                        permissions.add(permissionChecker);
                    }
                });
        return permissions;
    }

    private List<Permission> buildPermission(List<String> permissionIds) {
        return permissionRepository.findAllById(permissionIds);
    }

    private void checkPermissionIsValid(List<String> permissionIds) {
        List<Permission> permissions = buildPermission(permissionIds);
//        if (CollectionUtils.isEmpty(permissions)) {
//            throw new RuntimeException("Permission không tồn tại");
//        }
        List<String> listIdExists = permissions.stream().map(Permission::getId).collect(Collectors.toList());
        List<String> idNotExists = permissionIds.stream().filter(s -> !listIdExists.contains(s)
        ).collect(Collectors.toList());
        if (!idNotExists.isEmpty())
            throw new RuntimeException(String.format("Trong danh sách permisison ids có mã không tồn tại trên hệ thống: %s", idNotExists));
    }

    private void validateRoleExist(String id) {
        boolean isExist = roleRepository.existsById(id);
        if (!isExist)
            throw new RuntimeException(roleNotFound);
    }
}
