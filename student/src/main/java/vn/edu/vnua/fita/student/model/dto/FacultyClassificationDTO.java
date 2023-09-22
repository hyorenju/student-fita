package vn.edu.vnua.fita.student.model.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class FacultyClassificationDTO {
    private TermDTO term;
    private Integer dropoutWithoutPermission;
    private Integer dropoutWithPermission;
    private Integer excellent;
    private Integer good;
    private Integer fair;
    private Integer medium;
    private Integer weak;
    private Integer worst;
}
