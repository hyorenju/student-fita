package vn.edu.vnua.fita.student.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;

import java.sql.Timestamp;

@Data
public class TrashAdminDTO {
    private String id;

    private AdminDTO admin;

    @JsonFormat(pattern = DateTimeConstant.DATE_TIME_FORMAT)
    private Timestamp time;

    private String byWhom;
}
