package vn.edu.vnua.fita.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;

import java.sql.Timestamp;

@Data
public class TrashStudentDTO {
    private String id;

    private StudentDTO student;

    @JsonFormat(pattern = DateTimeConstant.DATE_TIME_FORMAT, timezone = DateTimeConstant.TIME_ZONE)
    private Timestamp time;

    private AdminDTO deletedBy;
}
