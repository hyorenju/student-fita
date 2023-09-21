package vn.edu.vnua.fita.student.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.entity.dto.ClassDTO;
import vn.edu.vnua.fita.student.entity.dto.CourseDTO;
import vn.edu.vnua.fita.student.entity.dto.MajorDTO;

import java.sql.Timestamp;

@Data
public class StudentLoginResponse extends BaseLoginResponse{
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

    public StudentLoginResponse(String jwt, String roleId, String id, String surname, String lastName, String avatar, CourseDTO course, MajorDTO major, ClassDTO aclass, Timestamp dob, String gender, String phoneNumber, String email, String homeTown, String residence, String fatherName, String fatherPhoneNumber, String motherName, String motherPhoneNumber) {
        super(jwt, roleId);
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
        this.email = email;
        this.homeTown = homeTown;
        this.residence = residence;
        this.fatherName = fatherName;
        this.fatherPhoneNumber = fatherPhoneNumber;
        this.motherName = motherName;
        this.motherPhoneNumber = motherPhoneNumber;
    }
}
