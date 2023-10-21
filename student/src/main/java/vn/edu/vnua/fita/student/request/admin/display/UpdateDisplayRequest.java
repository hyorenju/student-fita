package vn.edu.vnua.fita.student.request.admin.display;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UpdateDisplayRequest {
    private String img;

    private String title;

    private String content;
}
