package vn.edu.vnua.fita.student.response;

import lombok.Data;

import java.util.Map;

@Data
public class PermissionCheckResponse {

    private Map<String, Boolean> checkingPermissions;
}
