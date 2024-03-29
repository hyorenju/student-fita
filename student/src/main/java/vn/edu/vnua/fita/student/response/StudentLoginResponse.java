package vn.edu.vnua.fita.student.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.dto.ClassDTO;
import vn.edu.vnua.fita.student.dto.CourseDTO;
import vn.edu.vnua.fita.student.dto.MajorDTO;

import java.sql.Timestamp;

@Data
public class StudentLoginResponse extends BaseLoginResponse{
    private String refreshToken;
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
    private String familySituation;
    private String email;
    private String homeTown;
    private String residence;
    private String fatherName;
    private String fatherPhoneNumber;
    private String motherName;
    private String motherPhoneNumber;

    public StudentLoginResponse(String jwt, String roleId, String refreshToken, String id, String surname, String lastName, String avatar, CourseDTO course, MajorDTO major, ClassDTO aclass, Timestamp dob, String gender, String phoneNumber, String phoneNumber2, String familySituation, String email, String homeTown, String residence, String fatherName, String fatherPhoneNumber, String motherName, String motherPhoneNumber) {
        super(jwt, roleId);
        this.refreshToken = refreshToken;
        this.id = id;
        this.surname = surname;
        this.lastName = lastName;
        this.avatar = avatar;
        this.course = course;
        this.major = major;
        this.aclass = aclass;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.familySituation = familySituation;
        this.email = email;
        this.homeTown = homeTown;
        this.residence = residence;
        this.fatherName = fatherName;
        this.fatherPhoneNumber = fatherPhoneNumber;
        this.motherName = motherName;
        this.motherPhoneNumber = motherPhoneNumber;
    }
}
