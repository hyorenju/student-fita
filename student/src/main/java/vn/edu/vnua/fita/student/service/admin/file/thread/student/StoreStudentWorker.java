package vn.edu.vnua.fita.student.service.admin.file.thread.student;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.common.AppendCharacterConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Course;
import vn.edu.vnua.fita.student.entity.Major;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.MajorRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

@AllArgsConstructor
public class StoreStudentWorker implements Callable<StudentExcelData> {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final PasswordEncoder encoder;
    private final String studentStr;
    private final int row;

//    public StoreStudentWorker(StudentRepository studentRepository, CourseRepository courseRepository, ClassRepository classRepository, MajorRepository majorRepository, String studentStr, int row) {
//        this.studentRepository = studentRepository;
//        this.courseRepository = courseRepository;
//        this.classRepository = classRepository;
//        this.majorRepository = majorRepository;
//        this.studentStr = studentStr;
//        this.row = row;
//    }

    @Override
    public StudentExcelData call() throws ParseException {
        StudentExcelData studentExcelData = new StudentExcelData();

        if (!studentStr.isEmpty()) {
            String[] infoList = studentStr.strip().split(AppendCharacterConstant.APPEND_CHARACTER);

            String id = infoList[0].strip();
            String surname = infoList[1].strip();
            String lastName = infoList[2].strip();
            String courseId = infoList[3].strip();
            String majorId = infoList[4].strip();
            String classId = infoList[5].strip();
            String dob = infoList[6].strip();
            String gender = infoList[7].strip();
            String phoneNumber = infoList[8].strip();
            String homeTown = infoList[9].strip();
            String email = infoList[10].strip();
            String fatherName = infoList[11].strip();
            String fatherPhone = infoList[12].strip();
            String motherName = infoList[13].strip();
            String motherPhone = infoList[14].strip();

            Student student = Student.builder()
                    .id(id)
                    .surname(surname)
                    .lastName(lastName)
                    .course(Course.builder().id(courseId).build())
                    .major(Major.builder().id(majorId).build())
                    .aclass(AClass.builder().id(classId).build())
                    .dob(StringUtils.hasText(dob) ? MyUtils.convertTimestampFromExcel(dob) : null)
                    .gender(StringUtils.hasText(gender) ? gender : null)
                    .phoneNumber(StringUtils.hasText(phoneNumber) ? phoneNumber : null)
                    .email(StringUtils.hasText(email) ? email : null)
                    .homeTown(StringUtils.hasText(homeTown) ? homeTown : null)
                    .fatherName(StringUtils.hasText(fatherName) ? fatherName : null)
                    .fatherPhoneNumber(StringUtils.hasText(fatherPhone) ? fatherPhone : null)
                    .motherName(StringUtils.hasText(motherName) ? motherName : null)
                    .motherPhoneNumber(StringUtils.hasText(motherPhone) ? motherPhone : null)
                    .isDeleted(false)
                    .role(Role.builder().id(RoleConstant.STUDENT).build())
                    .password(StringUtils.hasText(dob) ? encoder.encode(MyUtils.formatDobToPassword(dob)) : encoder.encode("12345678"))
                    .familySituation("Không")
                    .build();

            List<StudentExcelData.ErrorDetail> errorDetailList = student.validateInformationDetailError(new CopyOnWriteArrayList<>());
//            if (studentRepository.existsById(id)) {
//                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sinh viên đã tồn tại").build());
//            }
            if (!courseRepository.existsById(courseId)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("Khoá không tồn tại").build());
            }
            if (!majorRepository.existsById(majorId)){
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("Ngành không tồn tại").build());
            }
            if (!classRepository.existsById(classId)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("Lớp không tồn tại").build());
            }
            if (studentRepository.existsByEmail(email)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(9).errorMsg("Email đã được sinh viên khác sử dụng").build());
            }

            studentExcelData.setStudent(student);
            if (!errorDetailList.isEmpty()) {
                studentExcelData.setErrorDetailList(errorDetailList);
                studentExcelData.setValid(false);
            }
            studentExcelData.setRowIndex(row);
        }

        return studentExcelData;
    }
}
