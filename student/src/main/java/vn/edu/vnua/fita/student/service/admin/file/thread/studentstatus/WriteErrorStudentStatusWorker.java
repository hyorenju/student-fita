package vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class WriteErrorStudentStatusWorker implements Callable<Void> {
    private Row row;
    private StudentStatus studentStatus;
    private CellStyle cellStyle;
    private StudentStatusExcelData studentStatusExcelData;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(studentStatus.getStudent().getId());
        row.createCell(1).setCellValue(studentStatus.getStatus().getName());
        row.createCell(2).setCellValue(MyUtils.convertTimestampToString(studentStatus.getTime()));
        row.createCell(3).setCellValue(studentStatus.getNote());

        studentStatusExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
