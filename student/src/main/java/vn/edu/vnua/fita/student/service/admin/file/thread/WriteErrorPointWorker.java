package vn.edu.vnua.fita.student.service.admin.file.thread;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class WriteErrorPointWorker implements Callable<Void> {
    private Row row;
    private Point point;
    private CellStyle cellStyle;
    private PointExcelData pointExcelData;

    @Override
    public Void call() throws Exception {
        return null;
    }
}
