package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.Status;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.repository.customrepo.CustomStudentStatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.request.admin.studentstatus.CreateStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.ExportStudentStatusRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.UpdateStudentStatusRequest;
import vn.edu.vnua.fita.student.service.admin.file.ExcelService;
import vn.edu.vnua.fita.student.service.admin.iservice.IStudentStatusService;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StudentStatusManager implements IStudentStatusService {
    private final StudentStatusRepository studentStatusRepository;
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final TermRepository termRepository;
    private final ExcelService excelService;
    private final String studentStatusHadExisted = "Trạng thái của sinh viên này đã tồn tại trong hệ thống";
    private final String studentStatusNotFound = "Trạng thái của sinh viên này không tồn tại trong hệ thống";
    private final String studentNotFound = "Sinh viên %s không tồn tại trong hệ thống";
    private final String statusNotFound = "Trạng thái này không tồn tại trong hệ thống";
    private final String timeNotValid = "Thời gian không hợp lệ";

    @Override
    public Page<StudentStatus> getStudentStatusList(GetStudentStatusListRequest request) {
        Specification<StudentStatus> specification = CustomStudentStatusRepository.filterStudentStatusList(
                request.getStudentId(),
                request.getFilter().getStatusId(),
                request.getFilter().getTermId()
        );
        return studentStatusRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public StudentStatus createStudentStatus(CreateStudentStatusRequest request) throws ParseException {
        String studentId = request.getStudent().getId();
        Integer statusId = request.getStatus().getId();
        if(studentStatusRepository.existsByStudentIdAndStatusId(studentId, statusId)){
            throw new RuntimeException(studentStatusHadExisted);
        }
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, studentId)));
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new RuntimeException(statusNotFound));

        StudentStatus studentStatus = StudentStatus.builder()
                .student(student)
                .status(status)
                .time(MyUtils.convertTimestampFromString(request.getTime()))
                .note(request.getNote())
                .build();

        String termId = MyUtils.createTermIdFromTimestamp(studentStatus.getTime());
        Term term = termRepository.findById(termId).orElseThrow(() -> new RuntimeException(timeNotValid));

        studentStatus.setTerm(term);
        return studentStatusRepository.saveAndFlush(studentStatus);
    }

    @Override
    public StudentStatus updateStudentStatus(UpdateStudentStatusRequest request, Long id) throws ParseException {
        StudentStatus studentStatus = studentStatusRepository.findById(id).orElseThrow(() -> new RuntimeException(studentStatusNotFound));
        String termId = MyUtils.createTermIdFromTimestamp(studentStatus.getTime());
        Term term = termRepository.findById(termId).orElseThrow(() -> new RuntimeException(timeNotValid));

        studentStatus.setTime(MyUtils.convertTimestampFromString(request.getTime()));
        studentStatus.setNote(request.getNote());
        studentStatus.setTerm(term);
        return studentStatusRepository.saveAndFlush(studentStatus);
    }

    @Override
    public StudentStatus deleteStudentStatus(Long id) {
        StudentStatus studentStatus = studentStatusRepository.findById(id).orElseThrow(() -> new RuntimeException(studentNotFound));
        studentStatusRepository.deleteById(id);
        return studentStatus;
    }

    @Override
    public List<StudentStatus> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return studentStatusRepository.saveAllAndFlush(excelService.readStudentStatusFromExcel(file));
    }

    @Override
    public String exportToExcel(ExportStudentStatusRequest request) {
        Specification<StudentStatus> specification = CustomStudentStatusRepository.filterStudentStatusList(
                request.getStudentId(),
                request.getFilter().getStatusId(),
                request.getFilter().getTermId()
        );
        List<StudentStatus> studentStatuses = studentStatusRepository.findAll(specification);

        return excelService.writeStudentStatusToExcel(studentStatuses);
    }

//    private String createTermId(Timestamp time) {
//        int term = (time.getMonth() >= Calendar.AUGUST) ? 1 : 2;
//        int year = term == 1 ? time.getYear() + 1900 : time.getYear() + 1899;
//        return "" + year + term;
//    }
}
