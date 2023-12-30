package vn.edu.vnua.fita.student.model.file;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.PointOfYear;

@Data
public class PointAnnualExcelData extends ExcelData{
    private PointOfYear pointOfYear;
}
