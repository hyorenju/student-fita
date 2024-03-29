package vn.edu.vnua.fita.student.service.admin.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.repository.customrepo.CustomStudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.student.*;
import vn.edu.vnua.fita.student.service.admin.file.ExcelService;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.admin.iservice.IStudentService;
import vn.edu.vnua.fita.student.util.MyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StudentManager implements IStudentService {
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final TrashStudentRepository trashStudentRepository;
    private final StudentRefresherRepository studentRefresherRepository;
    private final PointRepository pointRepository;
    private final PasswordEncoder encoder;
    private final ExcelService excelService;
    private final String studentHadExisted = "Sinh viên %s đã tồn tại trong hệ thống";
    private final String studentNotFound = "Không tìm thấy sinh viên %s";
    private final String courseNotFound = "Không tìm thấy khoá %s";
    private final String classNotFound = "Không tìm thấy lớp %s";
    private final String majorNotFound = "Không tìm thấy  %s";
    private final String byWhomNotFound = "Không thể xác định danh tính người xoá";
    private final String emailHasExisted = "Email %s đã tồn tại trong hệ thống, vui lòng sử dụng một email khác";


    @Override
    public Page<Student> getStudentList(GetStudentListRequest request) {
        Specification<Student> specification = CustomStudentRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getStudentId(),
                request.getFilter().getFamilySituation()
        );
        return studentRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student createStudent(CreateStudentRequest request) throws ParseException {
        if (studentRepository.existsById(request.getId())) {
            throw new RuntimeException(String.format(studentHadExisted, request.getId()));
        }
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(String.format(emailHasExisted, request.getEmail()));
        }
        Course course = courseRepository.findById(request.getCourse().getId()).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, request.getCourse().getId())));
        AClass aClass = classRepository.findById(request.getAclass().getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getAclass().getId())));
        Major major = majorRepository.findById(request.getMajor().getId()).orElseThrow(() -> new RuntimeException(String.format(majorNotFound, request.getMajor().getId())));

        Student student = Student.builder()
                .id(request.getId())
                .surname(request.getSurname())
                .lastName(request.getLastName())
                .course(course)
                .major(major)
                .aclass(aClass)
                .dob(MyUtils.convertTimestampFromString(request.getDob()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .phoneNumber2(request.getPhoneNumber2())
                .email(request.getEmail())
                .homeTown(request.getHomeTown())
                .familySituation(request.getFamilySituation())
                .residence(request.getResidence())
                .fatherName(request.getFatherName())
                .fatherPhoneNumber(request.getFatherPhoneNumber())
                .motherName(request.getMotherName())
                .motherPhoneNumber(request.getMotherPhoneNumber())
                .isDeleted(false)
                .role(Role.builder().id(RoleConstant.STUDENT).build())
                .build();

        if (StringUtils.hasText(request.getPassword())) {
            student.setPassword(encoder.encode(request.getPassword()));
        } else {
            student.setPassword(encoder.encode(MyUtils.formatDobToPassword(request.getDob())));
        }

        studentRepository.saveAndFlush(student);
//            createStudentStatus(student);

        return student;
    }

    @Override
    public Student updateStudent(UpdateStudentRequest request) throws ParseException {
        Student student = studentRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, request.getId())));
        Course course = courseRepository.findById(request.getCourse().getId()).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, request.getCourse().getId())));
        AClass aClass = classRepository.findById(request.getAclass().getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getAclass().getId())));
        Major major = majorRepository.findById(request.getMajor().getId()).orElseThrow(() -> new RuntimeException(String.format(majorNotFound, request.getMajor().getId())));
        if (!student.getEmail().equals(request.getEmail()) && studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(String.format(emailHasExisted, request.getEmail()));
        }

        student.setSurname(request.getSurname());
        student.setLastName(request.getLastName());
        student.setCourse(course);
        student.setMajor(major);
        student.setAclass(aClass);
        student.setDob(MyUtils.convertTimestampFromString(request.getDob()));
        student.setGender(request.getGender());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setPhoneNumber2(request.getPhoneNumber2());
        student.setHomeTown(request.getHomeTown());
        student.setFamilySituation(request.getFamilySituation());
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());
        if (StringUtils.hasText(request.getPassword())) {
            student.setPassword(encoder.encode(request.getPassword()));
        }
        studentRepository.saveAndFlush(student);

        return student;
    }

    @Override
    public TrashStudent deleteStudent(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));
        student.setIsDeleted(true);
        TrashStudent trashStudent = moveToTrash(student);
        studentRepository.saveAndFlush(student);
        return trashStudent;
    }

    @Override
    public List<TrashStudent> deleteManyStudent(DeleteStudentRequest request) {
        List<Student> students = checkIds(request.getIds());
        List<TrashStudent> trashStudents = new ArrayList<>();
        students.forEach(student -> {
            student.setIsDeleted(true);
            studentRepository.saveAndFlush(student);
            trashStudents.add(moveToTrash(student));
        });
        return trashStudents;
    }

    @Override
    public TrashStudent restoreStudent(Long id) {
        TrashStudent trashStudent = trashStudentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        Student student = trashStudent.getStudent();
        student.setIsDeleted(false);
        restoreFromTrash(student);
        studentRepository.saveAndFlush(student);
        return trashStudent;
    }

    @Override
    public List<TrashStudent> restoreManyStudent(RestoreStudentRequest request) {
        List<TrashStudent> trashStudents = trashStudentRepository.findAllById(request.getIds());
        trashStudents.forEach(trashStudent -> {
            Student student = trashStudent.getStudent();
            student.setIsDeleted(false);
            restoreFromTrash(student);
            studentRepository.saveAndFlush(student);
        });
        return trashStudents;
    }

    @Override
    public Page<TrashStudent> getTrashStudentList(GetTrashStudentRequest request) {
        return trashStudentRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public List<Student> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return studentRepository.saveAllAndFlush(excelService.readStudentFromExcel(file))
        /*.forEach(this::createStudentStatus)*/;
    }

    @Override
    public String exportToExcel(ExportStudentListRequest request) {
        Specification<Student> specification = CustomStudentRepository.filterStudentList(
                request.getFilter().getCourseId(),
                request.getFilter().getMajorId(),
                request.getFilter().getClassId(),
                request.getStudentId(),
                request.getFilter().getFamilySituation()
        );
        List<Student> students = studentRepository.findAll(specification);

        return excelService.writeStudentToExcel(students);
    }

    @Override
    public TrashStudent deletePermanent(Long id) {
        TrashStudent trashStudent = trashStudentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác để xoá vĩnh viễn"));
        Student student = trashStudent.getStudent();

        List<StudentStatus> studentStatuses = studentStatusRepository.findAllByStudent(student);
        for (StudentStatus studentStatus :
                studentStatuses) {
            studentStatusRepository.delete(studentStatus);
        }

        List<Point> points = pointRepository.findAllByStudent(student);
        for (Point point :
                points) {
            pointRepository.delete(point);
        }

        List<StudentRefresher> studentRefreshers = studentRefresherRepository.findAllByStudent(student);
        for (StudentRefresher studentRefresher :
                studentRefreshers) {
            studentRefresherRepository.delete(studentRefresher);
        }

        AClass aClass = classRepository.findByMonitor(student);
        if (aClass != null) {
            aClass.setMonitor(null);
            classRepository.saveAndFlush(aClass);
        }

        trashStudentRepository.delete(trashStudent);
        studentRepository.delete(student);

        return trashStudent;
    }

    public void createStudentStatus(Student student) {
        StudentStatus studentStatus = StudentStatus.builder()
                .student(student)
                .status(Status.builder().id(1).build())
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        int term = studentStatus.getTime().getMonth() >= 8 ? 2 : 1;
        int year = term == 1 ? studentStatus.getTime().getYear() + 1900 : studentStatus.getTime().getYear() + 1901;
        String termId = "" + year + term;
        studentStatus.setTerm(Term.builder().id(termId).build());
        studentStatusRepository.saveAndFlush(studentStatus);
    }

    private TrashStudent moveToTrash(Student student) {
        Admin admin = findAdminDeletedIt();
        TrashStudent trashStudent = TrashStudent.builder()
                .student(student)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .deletedBy(admin)
                .build();
        trashStudentRepository.saveAndFlush(trashStudent);
        return trashStudent;
    }

    private void restoreFromTrash(Student student) {
        TrashStudent trashStudent = trashStudentRepository.findByStudent(student);
        trashStudentRepository.deleteById(trashStudent.getId());
    }

    private List<Student> checkIds(List<String> ids) {
        List<Student> students = new ArrayList<>();
        ids.forEach(id -> {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, id)));
            students.add(student);
        });
        return students;
    }

    private Admin findAdminDeletedIt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(byWhomNotFound));
    }
}
