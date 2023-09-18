package vn.edu.vnua.fita.student.request.admin.point;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreatePointRequest {

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên quá dài")
    private String studentId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Size(min = 5, max = 5, message = "Mã học kỳ phải đủ 5 ký tự")
    private String termId;

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
}
