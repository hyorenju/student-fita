package vn.edu.vnua.fita.student.request.admin.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.EmailAnnotation;
import vn.edu.vnua.fita.student.domain.validator.InputDate;
import vn.edu.vnua.fita.student.domain.validator.PhoneNumber;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.fita.student.request.admin.major.CreateMajorRequest;

@Data
public class UpdateStudentRequest {
    private String id;

    @NotBlank(message = "Họ đệm không được để trống")
    @Size(max = 200, message = "Họ đệm quá dài")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Họ đệm không được chứa ký tự đặc biệt")
    private String surname;

    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 200, message = "Tên sinh viên quá dài")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Tên sinh viên không được chứa ký tự đặc biệt")
    private String lastName;

    private CreateCourseRequest course;

    private CreateMajorRequest major;

    private CreateClassRequest aclass;

    @NotBlank(message = "Ngày sinh không được để trống")
    @InputDate(message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    @Size(min = 8, max = 10, message = "Ngày sinh chỉ được 8-10 ký tự")
    private String dob;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneNumber(message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

//    @NotBlank(message = "Số điện thoại 2 không được để trống")
//    @PhoneNumber(message = "Số điện thoại 2 không đúng định dạng")
    private String phoneNumber2;

    @NotBlank(message = "Email không được để trống")
    @EmailAnnotation(message = "Email không đúng định dạng")
    @Size(max = 190,message = "Email quá dài")
    private String email;

    @NotBlank(message = "Quê quán không được để trống")
    @Size(max = 200,message = "Quê quán không được quá dài")
    private String homeTown;

    @NotBlank(message = "Vui lòng chọn hoàn cảnh gia đình sinh viên này")
    @Size(max = 200,message = "Hoàn cảnh quá dài")
    private String familySituation;

    @Size(max = 200,message = "Nơi ở hiện tại không được quá dài")
    private String residence;

    @Size(max = 200,message = "Tên bố không được quá dài")
    private String fatherName;

    private String fatherPhoneNumber;

    @Size(max = 200, message = "Tên mẹ không được quá dài")
    private String motherName;

    private String motherPhoneNumber;

    private String password;
}
