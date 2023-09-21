package vn.edu.vnua.fita.student.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;

import java.sql.Timestamp;

@Data
public class StudentStatusDTO {
    private String studentId;

    private String surname;

    private String lastName;

    private String statusName;

    @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT)
    private Timestamp time;

    private String note;
}
