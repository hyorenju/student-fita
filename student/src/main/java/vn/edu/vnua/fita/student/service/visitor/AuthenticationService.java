package vn.edu.vnua.fita.student.service.visitor;

import vn.edu.vnua.fita.student.response.BaseLoginResponse;

public interface AuthenticationService {
    BaseLoginResponse authenticateUser(String id, String password);
}
