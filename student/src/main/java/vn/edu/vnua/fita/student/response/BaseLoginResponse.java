package vn.edu.vnua.fita.student.response;

import lombok.Data;


@Data
public class BaseLoginResponse {
    private String jwt;

    private String roleId;

    public BaseLoginResponse(String jwt, String roleId) {
        this.jwt = jwt;
        this.roleId = roleId;
    }
}
