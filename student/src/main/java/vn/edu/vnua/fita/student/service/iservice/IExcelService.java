package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IExcelService {
    List<Student> readStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException, ArrayIndexOutOfBoundsException;
    String writeStudentToExcel(List<Student> students) throws IOException;
    String writePointToExcel(List<Point> points);
}
