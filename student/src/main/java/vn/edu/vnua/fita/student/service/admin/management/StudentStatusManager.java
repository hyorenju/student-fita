package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.entity.Status;
import vn.edu.vnua.fita.student.model.entity.Student;
import vn.edu.vnua.fita.student.model.entity.StudentStatus;
import vn.edu.vnua.fita.student.repository.customrepo.CustomStudentStatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.request.admin.student_status.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.student_status.UpdateStudentStatusRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IStudentStatusService;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class StudentStatusManager implements IStudentStatusService {
    private final StudentStatusRepository studentStatusRepository;
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final String studentStatusHadExisted = "Trạng thái của sinh viên này đã tồn tại trong hệ thống";
    private final String studentStatusNotFound = "Trạng thái của sinh viên này không tồn tại trong hệ thống";
    private final String studentNotFound = "Sinh viên %s không tồn tại trong hệ thống";
    private final String statusNotFound = "Trạng thái này không tồn tại trong hệ thống";

    @Override
    public Page<StudentStatus> getStudentStatusList(GetStudentStatusListRequest request) {
        Specification<StudentStatus> specification = CustomStudentStatusRepository.filterStudentStatusList(
                request.getStudentId(),
                request.getStatusId(),
                request.getTermId()
        );
        return studentStatusRepository.findAll(
                specification,
                PageRequest.of(request.getPage() - 1, request.getSize(),
                        Sort.by("time").ascending())
        );
    }

    @Override
    public StudentStatus createStudentStatus(CreateStudentStatusRequest request) {
        if(studentStatusRepository.existsByStudentIdAndStatusId(request.getStudentId(), request.getStatusId())){
            throw new RuntimeException(studentStatusHadExisted);
        }
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, request.getStudentId())));
        Status status = statusRepository.findById(request.getStatusId()).orElseThrow(() -> new RuntimeException(statusNotFound));

        StudentStatus studentStatus = StudentStatus.builder()
                .student(student)
                .status(status)
                .time(MyUtils.convertTimestampFromString(request.getTime()))
                .note(request.getNote())
                .build();
        studentStatus.setTermId(createTermId(studentStatus.getTime()));
        return studentStatusRepository.saveAndFlush(studentStatus);
    }

    @Override
    public StudentStatus updateStudentStatus(UpdateStudentStatusRequest request, Long id) {
        StudentStatus studentStatus = studentStatusRepository.findById(id).orElseThrow(() -> new RuntimeException(studentNotFound));
        studentStatus.setTime(MyUtils.convertTimestampFromString(request.getTime()));
        studentStatus.setNote(request.getNote());
        return studentStatusRepository.saveAndFlush(studentStatus);
    }

    @Override
    public StudentStatus deleteStudentStatus(Long id) {
        StudentStatus studentStatus = studentStatusRepository.findById(id).orElseThrow(() -> new RuntimeException(studentNotFound));
        studentStatusRepository.deleteById(id);
        return studentStatus;
    }

    public String createTermId(Timestamp time) {
        int term = time.getMonth() >= 8 ? 2 : 1;
        int year = term == 1 ? time.getYear() + 1900 : time.getYear() + 1901;
        return "" + year + term;
    }
}
