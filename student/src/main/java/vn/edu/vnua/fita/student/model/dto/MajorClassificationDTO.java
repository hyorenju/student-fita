package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;

@Data
public class MajorClassificationDTO {
    private MajorDTO major;
    private TermDTO term;
    private Integer excellent;
    private Integer good;
    private Integer fair;
    private Integer medium;
    private Integer weak;
    private Integer worst;
}
