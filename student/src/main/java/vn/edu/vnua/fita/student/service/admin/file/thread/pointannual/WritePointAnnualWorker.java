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
    private PointOfYear pointOfYear;

    @Override
    public Void call() throws Exception {
        Float avgPoint10 = pointOfYear.getAvgPoint10();
        Float avgPoint4 = pointOfYear.getAvgPoint10();
        Float trainingPoint = pointOfYear.getTrainingPoint();
        Integer creditsAcc = pointOfYear.getCreditsAcc();
        Float pointAcc10 = pointOfYear.getAvgPoint10();
        Float pointAcc4 = pointOfYear.getAvgPoint10();
        Integer creditsRegistered = pointOfYear.getCreditsRegistered();
        Integer creditsPassed = pointOfYear.getCreditsPassed();
        Integer creditsNotPassed = pointOfYear.getCreditsNotPassed();

        row.createCell(0).setCellValue(pointOfYear.getStudent().getId());
        row.createCell(1).setCellValue(pointOfYear.getStudent().getSurname());
        row.createCell(2).setCellValue(pointOfYear.getStudent().getLastName());
        row.createCell(3).setCellValue(pointOfYear.getStudent().getAclass().getId());
        row.createCell(4).setCellValue(pointOfYear.getYear().getId());
        row.createCell(5).setCellValue(avgPoint10 != null ? MyUtils.parseFloatToString(avgPoint10) : "");
        row.createCell(6).setCellValue(avgPoint4 != null ? MyUtils.parseFloatToString(avgPoint4) : "");
        row.createCell(7).setCellValue(trainingPoint != null ? MyUtils.parseFloatToString(trainingPoint) : "");
        row.createCell(8).setCellValue(creditsAcc != null ? "" + creditsAcc : "");
        row.createCell(9).setCellValue(pointAcc10 != null ? MyUtils.parseFloatToString(pointAcc10) : "");
        row.createCell(10).setCellValue(pointAcc4 != null ? MyUtils.parseFloatToString(pointAcc4) : "");
        row.createCell(11).setCellValue(creditsRegistered != null ? "" + creditsRegistered : "");
        row.createCell(12).setCellValue(creditsPassed != null ? "" + creditsPassed : "");
        row.createCell(13).setCellValue(creditsNotPassed != null ? "" + creditsNotPassed : "");
        return null;
    }
}
