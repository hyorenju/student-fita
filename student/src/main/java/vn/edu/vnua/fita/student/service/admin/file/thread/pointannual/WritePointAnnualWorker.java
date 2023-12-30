package vn.edu.vnua.fita.student.service.admin.file.thread.pointannual;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class WritePointAnnualWorker implements Callable<Void> {
    private Row row;
    private PointOfYear point;

    @Override
    public Void call() throws Exception {
        row.createCell(0).setCellValue(point.getStudent().getId());
        row.createCell(1).setCellValue(point.getStudent().getSurname());
        row.createCell(2).setCellValue(point.getStudent().getLastName());
        row.createCell(3).setCellValue(point.getStudent().getAclass().getId());
        row.createCell(4).setCellValue(point.getYear());
        row.createCell(5).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint10()));
        row.createCell(6).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint4()));
        row.createCell(7).setCellValue(point.getTrainingPoint());
        row.createCell(8).setCellValue(point.getCreditsAcc());
        row.createCell(9).setCellValue(MyUtils.parseFloatToString(point.getPointAcc10()));
        row.createCell(10).setCellValue(MyUtils.parseFloatToString(point.getPointAcc4()));
        row.createCell(11).setCellValue(point.getCreditsRegistered());
        row.createCell(12).setCellValue(point.getCreditsPassed());
        row.createCell(13).setCellValue(point.getCreditsNotPassed());
        return null;
    }
}
