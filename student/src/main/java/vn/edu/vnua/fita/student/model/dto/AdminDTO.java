package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private String id;
    private String name;
    private String email;
    private String roleId;
    private String avatar;
    private Boolean isDeleted;
}
