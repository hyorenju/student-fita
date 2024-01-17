package vn.edu.vnua.fita.student.request.admin.year;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateYearRequest {
    @NotBlank(message = "Năm học không được để trống")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "Năm học phải đúng định dạng. VD: 2016-2017")
    private String id;
}
