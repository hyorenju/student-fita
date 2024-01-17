package vn.edu.vnua.fita.student.service.admin.file.thread.aclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.file.ClassExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class WriteErrorClassWorker implements Callable<Void> {
    private Row row;
    private AClass aClass;
    private CellStyle cellStyle;
    private ClassExcelData classExcelData;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(aClass.getId());
        row.createCell(1).setCellValue(aClass.getName());

        classExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
