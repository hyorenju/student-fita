package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.vnua.fita.student.common.PermissionGroupConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.model.dto.RoleDTO;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.Permission;
import vn.edu.vnua.fita.student.model.authorization.PermissionChecker;
import vn.edu.vnua.fita.student.model.entity.Role;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
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
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final String roleNotFound = "Vai trò không tồn tại trong hệ thống";

    @Override
    public Role updateRole(String id, UpdateRoleRequest request) {
        if(!roleRepository.existsById(id)){
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
    public PermissionChecker checkPermissions(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException(roleNotFound));

        List<Permission> permissionList = permissionRepository.findAll();
        PermissionChecker response = new PermissionChecker();
        Map<String, Boolean> permissions = switch (id) {
            case RoleConstant.STUDENT -> mapResponse(role, permissionList, PermissionGroupConstant.STUDENT);
            case RoleConstant.MOD -> mapResponse(role, permissionList, PermissionGroupConstant.MOD);
            case RoleConstant.ADMIN -> mapResponse(role, permissionList, PermissionGroupConstant.ADMIN);
            default -> mapResponse(role, permissionList, PermissionGroupConstant.SUPERADMIN);
        };

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

    private List<Permission> buildPermission(List<String> permissionIds) {
        return permissionRepository.findAllById(permissionIds);
    }

    private void checkPermissionIsValid(List<String> permissionIds) {
        List<Permission> permissions = buildPermission(permissionIds);
        if (CollectionUtils.isEmpty(permissions)) {
            throw new RuntimeException("Permisison không tồn tại");
        }
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
