package vn.edu.vnua.fita.student.service.admin.file.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AllArgsConstructor;
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
    public StudentExcelData call() {
        StudentExcelData studentExcelData = new StudentExcelData();

        if (!studentStr.isEmpty()) {
            String[] infoList = studentStr.strip().split(",");
            String id = infoList[0].strip();
            String surname = infoList[1].strip();
            String lastName = infoList[2].strip();
            String courseId = infoList[3].strip();
            String majorId = infoList[4].strip();
            String classId = infoList[5].strip();
            String dob = infoList[6].strip();
            String gender = infoList[7].strip();
            String phoneNumber = infoList[8].strip();
            String email = infoList[9].strip();
            String homeTown = infoList[10].strip();

            Student student = Student.builder()
                    .id(id)
                    .surname(surname)
                    .lastName(lastName)
                    .course(Course.builder().id(courseId).build())
                    .major(Major.builder().id(majorId).build())
                    .aclass(AClass.builder().id(classId).build())
                    .dob(MyUtils.convertTimestampFromString(dob))
                    .gender(gender)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .homeTown(homeTown)
                    .isDeleted(false)
                    .role(Role.builder().id(RoleConstant.STUDENT).build())
                    .build();

            List<StudentExcelData.ErrorDetail> errorDetailList = student.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if (studentRepository.existsById(id)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã đã tồn tại").build());
            }
            if (!courseRepository.existsById(courseId)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("Khoá không tồn tại").build());
            }
            if (!majorRepository.existsById(majorId)){
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("Ngành không tồn tại").build());
            }
            if (!classRepository.existsById(classId)) {
                errorDetailList.add(StudentExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("Lớp không tồn tại").build());
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
