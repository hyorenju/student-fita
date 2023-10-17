package vn.edu.vnua.fita.student.model.file;

import lombok.Data;
import vn.edu.vnua.fita.student.entity.Student;

@Data
public class StudentExcelData extends ExcelData{
    private Student student;
}
