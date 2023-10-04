package vn.edu.vnua.fita.student.model.dto;

import lombok.Data;

@Data
public class FacultyColumnChartDTO {
    private TermDTO term;
    private Integer dropoutWithoutPermission;
    private Integer dropoutWithPermission;
}
