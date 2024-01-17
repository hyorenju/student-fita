package vn.edu.vnua.fita.student.model.file;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.StudentStatus;

@Data
public class StudentStatusExcelData extends ExcelData{
    private StudentStatus studentStatus;
}
