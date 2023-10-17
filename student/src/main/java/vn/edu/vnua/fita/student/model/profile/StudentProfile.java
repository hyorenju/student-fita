package vn.edu.vnua.fita.student.model.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.dto.ClassDTO;
import vn.edu.vnua.fita.student.dto.CourseDTO;
import vn.edu.vnua.fita.student.dto.MajorDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class StudentProfile {
    private String id;

    private String surname;

    private String lastName;

    private String avatar;

    private CourseDTO course;

    private MajorDTO major;

    private ClassDTO aclass;

    @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT)
    private Timestamp dob;

    private String gender;

    private String phoneNumber;

    private String email;

    private String homeTown;

    private String residence;

    private String fatherName;

    private String fatherPhoneNumber;

    private String motherName;

    private String motherPhoneNumber;

    private List<StudentStatusProfile> statuses;

    @Data
    @Builder
    public static class StudentStatusProfile {
        private String statusName;

        @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT)
        private Timestamp time;

        private String note;
    }
}
