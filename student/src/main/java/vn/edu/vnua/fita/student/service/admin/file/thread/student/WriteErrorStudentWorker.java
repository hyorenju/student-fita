package vn.edu.vnua.fita.student.service.admin.file.thread.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class WriteErrorStudentWorker implements Callable<Void> {
    private Row row;
    private Student student;
    private CellStyle cellStyle;
    private StudentExcelData studentExcelData;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(student.getId());
        row.createCell(1).setCellValue(student.getSurname());
        row.createCell(2).setCellValue(student.getLastName());
        row.createCell(3).setCellValue(student.getCourse().getId());
        row.createCell(4).setCellValue(student.getMajor().getId());
        row.createCell(5).setCellValue(student.getAclass().getId());
        row.createCell(6).setCellValue(student.getDob() != null ? MyUtils.convertTimestampToString(student.getDob()) : "");
        row.createCell(7).setCellValue(student.getGender() != null ? student.getGender() : "");
        row.createCell(8).setCellValue(student.getPhoneNumber() != null ? student.getPhoneNumber() : "");
        row.createCell(9).setCellValue(student.getHomeTown() != null ? student.getHomeTown() : "");
        row.createCell(10).setCellValue(student.getEmail() != null ? student.getEmail() : "");
        row.createCell(11).setCellValue(student.getFatherName() != null ? student.getFatherName() : "");
        row.createCell(12).setCellValue(student.getFatherPhoneNumber() != null ? student.getFatherPhoneNumber() : "");
        row.createCell(13).setCellValue(student.getMotherName() != null ? student.getMotherName() : "");
        row.createCell(14).setCellValue(student.getMotherPhoneNumber() != null ? student.getMotherPhoneNumber() : "");
        row.createCell(15).setCellValue("");

        studentExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
