package vn.edu.vnua.fita.student.request.admin.display;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class CreateDisplayRequest {
    private String img;

    private String title;

    private String content;

    @NotBlank(message = "Miêu tả nơi hiển thị không được để trống")
    private String location;
}
