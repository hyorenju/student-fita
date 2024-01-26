package vn.edu.vnua.fita.student.request.admin.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateRoleRequest {
    private String id;
}
