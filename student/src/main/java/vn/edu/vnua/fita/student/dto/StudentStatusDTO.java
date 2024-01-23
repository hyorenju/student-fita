package vn.edu.vnua.fita.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;

import java.sql.Timestamp;

@Data
public class StudentStatusDTO {
    private Long id;

    private StudentDTO student;

    private StatusDTO status;

    @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT, timezone = DateTimeConstant.TIME_ZONE)
    private Timestamp time;

    private TermDTO term;

    private String note;
}
