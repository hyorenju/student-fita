package vn.edu.vnua.fita.student.request.admin.statistic;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetStatisticRequest {
    @NotBlank(message = "Start không được để trống")
    private String start;

    @NotBlank(message = "End không được để trống")
    private String end;
}
