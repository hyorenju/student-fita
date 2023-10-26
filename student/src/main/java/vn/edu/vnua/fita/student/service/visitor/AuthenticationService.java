package vn.edu.vnua.fita.student.service.visitor;

import vn.edu.vnua.fita.student.response.BaseLoginResponse;

import java.io.IOException;

public interface AuthenticationService {
    BaseLoginResponse authenticateUser(String username, String password);
    BaseLoginResponse verifyExpiration(String token) throws IOException;
}
