package vn.edu.vnua.fita.student.service.admin.file.thread.point;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class WritePointWorker implements Callable<Void> {
    private Row row;
    private Point point;

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
        row.createCell(1).setCellValue(point.getStudent().getSurname());
        row.createCell(2).setCellValue(point.getStudent().getLastName());
        row.createCell(3).setCellValue(point.getStudent().getAclass().getId());
        row.createCell(4).setCellValue(point.getTerm().getId());
        row.createCell(2).setCellValue(avgPoint10 != null ? MyUtils.parseFloatToString(avgPoint10) : "");
        row.createCell(3).setCellValue(avgPoint4 != null ? MyUtils.parseFloatToString(avgPoint4) : "");
        row.createCell(4).setCellValue(trainingPoint != null ? "" + trainingPoint : "");
        row.createCell(5).setCellValue(creditsAcc != null ? "" + creditsAcc : "");
        row.createCell(6).setCellValue(pointAcc10 != null ? MyUtils.parseFloatToString(pointAcc10) : "");
        row.createCell(7).setCellValue(pointAcc4 != null ? MyUtils.parseFloatToString(pointAcc4) : "");
        row.createCell(8).setCellValue(creditsRegistered != null ? "" + creditsRegistered : "");
        row.createCell(9).setCellValue(creditsPassed != null ? "" + creditsPassed : "");
        row.createCell(10).setCellValue(creditsNotPassed != null ? "" + creditsNotPassed : "");
        return null;
    }
}
