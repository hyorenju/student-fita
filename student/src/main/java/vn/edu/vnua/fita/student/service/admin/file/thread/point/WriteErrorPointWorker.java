package vn.edu.vnua.fita.student.service.admin.file.thread.point;

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
        Float avgPoint10 = point.getAvgPoint10();
        Float avgPoint4 = point.getAvgPoint10();
        Integer trainingPoint = point.getTrainingPoint();
        Integer creditsAcc = point.getCreditsAcc();
        Float pointAcc10 = point.getAvgPoint10();
        Float pointAcc4 = point.getAvgPoint10();
        Integer creditsRegistered = point.getCreditsRegistered();
        Integer creditsPassed = point.getCreditsPassed();
        Integer creditsNotPassed = point.getCreditsNotPassed();

        row.createCell(0).setCellValue(point.getStudent().getId());
        row.createCell(1).setCellValue(point.getTerm().getId());
        row.createCell(2).setCellValue(avgPoint10 != null ? MyUtils.parseFloatToString(avgPoint10) : "");
        row.createCell(3).setCellValue(avgPoint4 != null ? MyUtils.parseFloatToString(avgPoint4) : "");
        row.createCell(4).setCellValue(trainingPoint != null ? "" + trainingPoint : "");
        row.createCell(5).setCellValue(creditsAcc != null ? "" + creditsAcc : "");
        row.createCell(6).setCellValue(pointAcc10 != null ? MyUtils.parseFloatToString(pointAcc10) : "");
        row.createCell(7).setCellValue(pointAcc4 != null ? MyUtils.parseFloatToString(pointAcc4) : "");
        row.createCell(8).setCellValue(creditsRegistered != null ? "" + creditsRegistered : "");
        row.createCell(9).setCellValue(creditsPassed != null ? "" + creditsPassed : "");
        row.createCell(10).setCellValue(creditsNotPassed != null ? "" + creditsNotPassed : "");
        row.createCell(11);

        pointExcelData.getErrorDetailList().forEach(errorDetail -> {
            Cell cell = row.getCell(errorDetail.getColumnIndex());
            cell.setCellStyle(cellStyle);
            cell.setCellValue(errorDetail.getErrorMsg());
        });
        return null;
    }
}
