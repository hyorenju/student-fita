package vn.edu.vnua.fita.student.service.admin.file.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class WriteErrorPointWorker implements Callable<Void> {
    private Row row;
    private Point point;
    private CellStyle cellStyle;
    private PointExcelData pointExcelData;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(point.getStudentId());
        row.createCell(1).setCellValue(point.getTermId());
        row.createCell(2).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint10()));
        row.createCell(3).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint4()));
        row.createCell(4).setCellValue(point.getTrainingPoint());
        row.createCell(5).setCellValue(point.getCreditsAcc());
        row.createCell(6).setCellValue(MyUtils.parseFloatToString(point.getPointAcc10()));
        row.createCell(7).setCellValue(MyUtils.parseFloatToString(point.getPointAcc4()));
        row.createCell(8);

        pointExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
