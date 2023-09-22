package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;

@Data
public class ClassClassificationDTO {
    private ClassDTO aclass;
    private TermDTO term;
    private Integer excellent;
    private Integer good;
    private Integer fair;
    private Integer medium;
    private Integer weak;
    private Integer worst;
}
