package vn.edu.vnua.fita.student.entity;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.domain.validator.ImportStudentValidator;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(length = 200)
    private String surname;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "avatar", length = 1000)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private AClass aclass;

    @Column
    private Timestamp dob;

    @Column(length = 200)
    private String gender;

    @Column(name = "phone_number", length = 200)
    private String phoneNumber;

    @Column(name = "new_phone", length = 200)
    private String newPhone;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(length = 190, unique = true)
    private String email;

    @Column(name = "home_town", length = 200)
    private String homeTown;

    @Column(length = 200)
    private String residence;

    @Column(name = "father_name", length = 200)
    private String fatherName;

    @Column(name = "father_phone_number", length = 200)
    private String fatherPhoneNumber;

    @Column(name = "mother_name", length = 200)
    private String motherName;

    @Column(name = "mother_phone_number", length = 200)
    private String motherPhoneNumber;

    @Column(length = 200)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role = Role.builder().id(RoleConstant.STUDENT).build();

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "student")
    private Collection<Document> documents;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "students_terms",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "students_statuses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Collection<Status> statuses;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getId()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }

    public List<StudentExcelData.ErrorDetail> validateInformationDetailError(List<StudentExcelData.ErrorDetail> errorDetailList){
        if (!ImportStudentValidator.validateId(id)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã không hợp lệ").build());
        }
        if (!ImportStudentValidator.validateName(surname)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Họ đệm không hợp lệ").build());
        }
        if (!ImportStudentValidator.validateName(lastName)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(2).errorMsg("Tên không hợp lệ").build());
        }
//        if (!ImportStudentValidator.validateCourse(course.getId())) {
//            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("Khoá không hợp lệ").build());
//        }
//        if (!ImportStudentValidator.validateMajor(major.getId())) {
//            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("Ngành không hợp lệ").build());
//        }
//        if (!ImportStudentValidator.validateClass(aclass.getId())) {
//            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("Lớp không hợp lệ").build());
//        }
        if (!ImportStudentValidator.validateDob(dob)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(6).errorMsg("Dạng dd/MM/yyyy").build());
        }
        if (!ImportStudentValidator.validateGender(gender)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(7).errorMsg("Giới tính không hợp lệ").build());
        }
        if (!ImportStudentValidator.validatePhoneNumber(phoneNumber)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(8).errorMsg("Sđt không hợp lệ").build());
        }
//        if (!ImportStudentValidator.validateEmail(email)) {
//            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(9).errorMsg("Email không hợp lệ").build());
//        }
        if (!ImportStudentValidator.validateHomeTown(homeTown)) {
            errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(9).errorMsg("Quê quán không hợp lệ").build());
        }

        return errorDetailList;
    }




}
