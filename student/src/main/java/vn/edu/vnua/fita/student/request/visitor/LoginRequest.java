package vn.edu.vnua.fita.student.request.visitor;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
}
