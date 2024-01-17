package vn.edu.vnua.fita.student.service.admin.file.thread.pointannual;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.model.file.PointAnnualExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@Data
@AllArgsConstructor
public class WriteErrorPointAnnualWorker implements Callable<Void> {
    private Row row;
    private PointOfYear pointOfYear;
    private CellStyle cellStyle;
    private PointAnnualExcelData pointAnnualExcelData;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(pointOfYear.getStudent().getId());
        row.createCell(1).setCellValue(pointOfYear.getYear().getId());
        row.createCell(2).setCellValue(MyUtils.parseFloatToString(pointOfYear.getAvgPoint10()));
        row.createCell(3).setCellValue(MyUtils.parseFloatToString(pointOfYear.getAvgPoint4()));
        row.createCell(4).setCellValue(pointOfYear.getTrainingPoint());
        row.createCell(5).setCellValue(pointOfYear.getCreditsAcc());
        row.createCell(6).setCellValue(MyUtils.parseFloatToString(pointOfYear.getPointAcc10()));
        row.createCell(7).setCellValue(MyUtils.parseFloatToString(pointOfYear.getPointAcc4()));
        row.createCell(8).setCellValue(pointOfYear.getCreditsRegistered());
        row.createCell(9).setCellValue(pointOfYear.getCreditsPassed());
        row.createCell(10).setCellValue(pointOfYear.getCreditsNotPassed());
        row.createCell(11);

        pointAnnualExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
