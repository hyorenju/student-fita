package vn.edu.vnua.fita.student.model.file;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.StudentStatus;

@Data
public class ClassExcelData extends ExcelData{
    private AClass aClass;
}
