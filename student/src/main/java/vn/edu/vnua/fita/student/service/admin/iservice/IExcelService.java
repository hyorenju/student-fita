package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IExcelService {
    List<Student> readStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException, ArrayIndexOutOfBoundsException;
    String writeStudentToExcel(List<Student> students) throws IOException;

    List<Point> readPointFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String writePointToExcel(List<Point> points);

    List<PointOfYear> readPointAnnualFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String writePointAnnualToExcel(List<PointOfYear> pointOfYears);

    List<StudentStatus> readStudentStatusFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String writeStudentStatusToExcel(List<StudentStatus> studentStatuses);

    List<AClass> readClassFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String writeClassListToExcel(List<AClass> classes);
}
