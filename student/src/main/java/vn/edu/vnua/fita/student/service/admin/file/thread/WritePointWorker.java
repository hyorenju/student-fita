package vn.edu.vnua.fita.student.service.admin.file.thread;

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
        row.createCell(0).setCellValue(point.getStudentId());
        row.createCell(1).setCellValue(point.getSurname());
        row.createCell(2).setCellValue(point.getLastName());
        row.createCell(3).setCellValue(point.getTermId());
        row.createCell(4).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint10()));
        row.createCell(5).setCellValue(MyUtils.parseFloatToString(point.getAvgPoint4()));
        row.createCell(6).setCellValue(point.getTrainingPoint());
        row.createCell(7).setCellValue(point.getCreditsAcc());
        row.createCell(8).setCellValue(MyUtils.parseFloatToString(point.getPointAcc10()));
        row.createCell(9).setCellValue(MyUtils.parseFloatToString(point.getPointAcc4()));
        return null;
    }
}
