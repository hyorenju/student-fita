package vn.edu.vnua.fita.student.request.admin.major;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class UpdateMajorRequest {
    private String id;

    @NotBlank(message = "Tên chuyên ngành không được để trống")
    private String name;

    @NotNull(message = "Số lượng tín chỉ của chuyên ngành không được để trống")
    private Integer totalCredits;
}
