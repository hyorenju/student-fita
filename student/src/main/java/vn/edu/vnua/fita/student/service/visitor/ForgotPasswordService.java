package vn.edu.vnua.fita.student.service.visitor;


import vn.edu.vnua.fita.student.request.visitor.ForgotPasswordRequest;
import vn.edu.vnua.fita.student.request.visitor.SendMailRequest;

public interface ForgotPasswordService {
    void sendMessage(SendMailRequest request);
    boolean checkToken(String token);
    void changePassword(ForgotPasswordRequest request);

}
