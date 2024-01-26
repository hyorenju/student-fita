package vn.edu.vnua.fita.student.request.admin.statistic;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetCircleStatisticRequest {
    @NotBlank(message = "Thời gian không được để trống")
    private String time;
}
