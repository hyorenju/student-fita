package vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus;

import lombok.AllArgsConstructor;
import vn.edu.vnua.fita.student.common.AppendCharacterConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentStatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StoreStudentStatusWorker implements Callable<StudentStatusExcelData> {
    private final StudentStatusRepository studentStatusRepository;
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final TermRepository termRepository;
    private final String studentStatusStr;
    private final int row;


    @Override
    public StudentStatusExcelData call() throws Exception {
        StudentStatusExcelData studentStatusExcelData = new StudentStatusExcelData();

        if (!studentStatusStr.isEmpty()) {
            String[] infoList = studentStatusStr.strip().split(AppendCharacterConstant.APPEND_CHARACTER);
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
            Status status = Status.builder().id(1000).name(statusName).build();
            if(statusOptional.isPresent()) {
                status = statusOptional.get();
            }

            Timestamp date = MyUtils.convertTimestampFromExcel(time);
            String termId = MyUtils.createTermIdFromTimestamp(date);
            Optional<Term> termOptional = termRepository.findById(termId);
            Term term = Term.builder().id(termId).build();
            if(termOptional.isPresent()){
                term = termOptional.get();
            }

            StudentStatus studentStatus;
            Optional<StudentStatus> studentStatusOptional = studentStatusRepository.findByStudentIdAndTimeAndStatusId(studentId, date, status.getId());
            if(studentStatusOptional.isPresent()){
                studentStatus = studentStatusOptional.get();
                studentStatus.setNote(note);
            } else {
                studentStatus = StudentStatus.builder()
                        .student(student)
                        .status(status)
                        .time(date)
                        .note(note)
                        .term(term)
                        .build();
            }

            List<StudentStatusExcelData.ErrorDetail> errorDetailList = studentStatus.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(statusOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Trạng thái không tồn tại").build());
            }
            if(termOptional.isEmpty()) {
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(2).errorMsg("Thời gian không hợp lệ").build());
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
