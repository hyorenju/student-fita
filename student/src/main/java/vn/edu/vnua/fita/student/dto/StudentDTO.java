package vn.edu.vnua.fita.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class StudentDTO {
    private String id;

    private String surname;

    private String lastName;

    private String avatar;

    private CourseDTO course;

    private MajorDTO major;

    private ClassDTO aclass;

    @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT, timezone = DateTimeConstant.TIME_ZONE)
    private Timestamp dob;

    private String gender;

    private String phoneNumber;

    private String phoneNumber2;

    private String email;

    private String homeTown;

    private String familySituation;

    private String residence;

    private String fatherName;

    private String fatherPhoneNumber;

    private String motherName;

    private String motherPhoneNumber;

    private Boolean isDeleted;
}
