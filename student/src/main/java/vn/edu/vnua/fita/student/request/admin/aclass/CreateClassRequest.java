package vn.edu.vnua.fita.student.request.admin.aclass;

import lombok.Data;
import jakarta.validation.constraints.*;
import vn.edu.vnua.fita.student.common.IdentifyPatternConstant;
import vn.edu.vnua.fita.student.entity.Student;


@Data
public class CreateClassRequest {
    @NotBlank(message = "Mã lớp không được để trống")
    @Pattern(regexp = IdentifyPatternConstant.CLASS_ID_PATTERN, message = "Mã lớp phải có dạng 'Mã_khóa + mã_ngành + định_danh_lớp'. (Ví dụ: K65CNTTA)")
    private String id;

    @NotBlank(message = "Tên lớp không được để trống")
    private String name;

    private Monitor monitor;

    private ViceMonitor viceMonitor;

    private Secretary secretary;

    private DeputySecretary deputySecretary;

    @Data
    public static class Monitor{
        private String id;
    }

    @Data
    public static class ViceMonitor{
        private String id;
    }

    @Data
    public static class Secretary{
        private String id;
    }

    @Data
    public static class DeputySecretary{
        private String id;
    }
}
