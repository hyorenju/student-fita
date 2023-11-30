package vn.edu.vnua.fita.student.request.admin.aclass;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class UpdateClassRequest {
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
