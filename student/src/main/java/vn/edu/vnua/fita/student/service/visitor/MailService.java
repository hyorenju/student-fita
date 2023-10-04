package vn.edu.vnua.fita.student.service.visitor;

public interface MailService {
    void sendEmailText(String to, String link);
    void sendEmailHtml(String to, String link, String token);
}
