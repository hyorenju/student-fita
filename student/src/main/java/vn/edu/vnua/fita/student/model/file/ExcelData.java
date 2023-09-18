package vn.edu.vnua.fita.student.model.file;

import lombok.Builder;
import lombok.Data;
import vn.edu.vnua.fita.student.entity.Student;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelData {
    private int rowIndex;
    private Student student;
    private List<ErrorDetail> errorDetailList = new ArrayList<>();
    private boolean isValid = true;

    @Data
    @Builder
    public static class ErrorDetail{
        private int columnIndex;
        private String errorMsg;
    }
}
