package vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class WriteStudentStatusWorker implements Callable<Void> {
    private Row row;
    private StudentStatus studentStatus;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(studentStatus.getStudent().getId());
        row.createCell(1).setCellValue(studentStatus.getStatus().getName());
        row.createCell(2).setCellValue(MyUtils.convertTimestampToString(studentStatus.getTime()));
        row.createCell(3).setCellValue(studentStatus.getTermId());
        row.createCell(4).setCellValue(studentStatus.getNote());
        return null;
    }
}
