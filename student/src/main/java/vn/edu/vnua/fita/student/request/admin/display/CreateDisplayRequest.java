package vn.edu.vnua.fita.student.request.admin.display;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class CreateDisplayRequest {
    @NotBlank(message = "Ảnh hiển thị không được để trống")
    private String img;

    @NotBlank(message = "Tiêu đề hiển thị không được để trống")
    private String title;

    @NotBlank(message = "Nội dung hiển thị không được để trống")
    private String content;

    @NotBlank(message = "Miêu tả nơi hiển thị không được để trống")
    private String location;
}
