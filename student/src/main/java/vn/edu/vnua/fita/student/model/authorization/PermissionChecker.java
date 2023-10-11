package vn.edu.vnua.fita.student.model.authorization;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PermissionChecker {
    private String id;
    private String name;
    private Boolean isAllowed;
}
