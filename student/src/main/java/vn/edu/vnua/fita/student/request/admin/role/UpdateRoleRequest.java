package vn.edu.vnua.fita.student.request.admin.role;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleRequest {
    private List<String> permissionIds;
}