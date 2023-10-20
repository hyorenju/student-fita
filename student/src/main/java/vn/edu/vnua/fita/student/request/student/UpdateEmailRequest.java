package vn.edu.vnua.fita.student.request.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.EmailAnnotation;

@Data
public class UpdateEmailRequest {
    @NotBlank(message = "Email không được để trống")
    @EmailAnnotation(message = "Email không đúng định dạng")
    @Size(max = 190, message = "Email quá dài")
    private String email;
}
