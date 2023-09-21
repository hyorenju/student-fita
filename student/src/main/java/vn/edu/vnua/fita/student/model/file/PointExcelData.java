package vn.edu.vnua.fita.student.model.file;

import lombok.Data;
import vn.edu.vnua.fita.student.model.entity.Point;

@Data
public class PointExcelData extends ExcelData{
    private Point point;
}
