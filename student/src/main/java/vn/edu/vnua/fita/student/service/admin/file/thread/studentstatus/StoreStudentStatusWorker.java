package vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus;

import lombok.AllArgsConstructor;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StoreStudentStatusWorker implements Callable<StudentStatusExcelData> {
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final String studentStatusStr;
    private final int row;


    @Override
    public StudentStatusExcelData call() throws Exception {
        StudentStatusExcelData studentStatusExcelData = new StudentStatusExcelData();

        if (!studentStatusStr.isEmpty()) {
            String[] infoList = studentStatusStr.strip().split(",");
            String studentId = infoList[0].strip();
            String statusName = infoList[1].strip();
            String time = infoList[2].strip();
            String note = infoList[3].strip();

            Optional<Student> studentOptional = studentRepository.findById(studentId);
            Student student = Student.builder().id(studentId).build();
            if (studentOptional.isPresent()) {
                student = studentOptional.get();
            }

            Optional<Status> statusOptional = statusRepository.findByName(statusName);
            Status status = Status.builder().name(statusName).build();
            if(statusOptional.isPresent()) {
                status = statusOptional.get();
            }

            Timestamp date = MyUtils.convertTimestampFromExcel(time);
            String termId = MyUtils.createTermIdFromTimestamp(date);

            StudentStatus studentStatus = StudentStatus.builder()
                    .student(student)
                    .status(status)
                    .time(date)
                    .note(note)
                    .termId(termId)
                    .build();

            List<StudentStatusExcelData.ErrorDetail> errorDetailList = studentStatus.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(statusOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Trạng thái không tồn tại").build());
            }

            studentStatusExcelData.setStudentStatus(studentStatus);
            if (!errorDetailList.isEmpty()) {
                studentStatusExcelData.setErrorDetailList(errorDetailList);
                studentStatusExcelData.setValid(false);
            }
            studentStatusExcelData.setRowIndex(row);
        }

        return studentStatusExcelData;
    }
}
