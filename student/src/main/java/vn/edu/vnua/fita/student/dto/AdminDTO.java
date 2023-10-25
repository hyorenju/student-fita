package vn.edu.vnua.fita.student.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private String id;
    private String name;
    private String email;
    private RoleDTO role;
    private String avatar;
    private Boolean isDeleted;
}
