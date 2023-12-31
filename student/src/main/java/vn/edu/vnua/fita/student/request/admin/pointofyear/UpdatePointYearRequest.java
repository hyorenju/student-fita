package vn.edu.vnua.fita.student.request.admin.pointofyear;

import jakarta.validation.constraints.*;
import lombok.Data;
import vn.edu.vnua.fita.student.request.admin.student.CreateStudentRequest;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;


@Data
public class UpdatePointYearRequest {
    private CreateStudentRequest student;

    @NotBlank(message = "Năm học không được để trống")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "Năm học phải đúng định dạng. VD: 2016-2017")
    private String year;

    @NotNull(message = "Điểm trung bình hệ 10 không được để trống")
    @Min(value = 0, message = "Điểm trung bình hệ 10 không được dưới 0")
    @Max(value = 10, message = "Điểm trung bình hệ 10 không được quá 10")
    private Float avgPoint10;

    @NotNull(message = "Điểm trung bình hệ 4 không được để trống")
    @Min(value = 0, message = "Điểm trung bình hệ 4 không được dưới 0")
    @Max(value = 4, message = "Điểm trung bình hệ 4 không được quá 4")
    private Float avgPoint4;

    @NotNull(message = "Điểm rèn luyện không được để trống")
    @Min(value = 0, message = "Điểm rèn luyện không được dưới 0")
    @Max(value = 100, message = "Điểm rèn luyện không được quá 100")
    private Integer trainingPoint;

    @NotNull(message = "Tín chỉ tích lũy không được để trống")
    @Min(value = 0, message = "Tín chỉ tích lũy không được dưới 0")
    private Integer creditsAcc;

    @NotNull(message = "Điểm trung bình tích lũy hệ 10 không được để trống")
    @Min(value = 0, message = "Điểm trung bình tích lũy hệ 10 không được dưới 0")
    @Max(value = 10, message = "Điểm trung bình tích lũy hệ 10 không được quá 10")
    private Float pointAcc10;

    @NotNull(message = "Điểm trung bình tích lũy hệ 4 không được để trống")
    @Min(value = 0, message = "Điểm trung bình tích lũy hệ 4 không được dưới 0")
    @Max(value = 4, message = "Điểm trung bình tích lũy hệ 4 không được quá 4")
    private Float pointAcc4;

    @NotNull(message = "Số tín chỉ đăng ký không được để trống")
    @Min(value = 0, message = "Số tín chỉ đăng ký không được dưới 0")
    private Integer creditsRegistered;

    @NotNull(message = "Số tín chỉ đạt không được để trống")
    @Min(value = 0, message = "Số tín chỉ đạt không được dưới 0")
    private Integer creditsPassed;

    @NotNull(message = "Số tín chỉ không đạt không được để trống")
    @Min(value = 0, message = "Số tín chỉ không đạt không được dưới 0")
    private Integer creditsNotPassed;
}
