package vn.edu.vnua.fita.student.request.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.PhoneNumber;

@Data
public class UpdatePhoneNumberRequest {
//    @NotBlank(message = "Số điện thoại không được để trống")
//    @PhoneNumber(message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;
}
