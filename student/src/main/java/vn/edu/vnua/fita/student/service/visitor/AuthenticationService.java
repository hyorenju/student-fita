package vn.edu.vnua.fita.student.service.visitor;

import vn.edu.vnua.fita.student.response.BaseLoginResponse;

public interface AuthenticationService {
    String authenticateUser(String username, String password);
    BaseLoginResponse verifyExpiration(String token);
}
