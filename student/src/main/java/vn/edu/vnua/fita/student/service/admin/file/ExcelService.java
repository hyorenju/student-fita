package vn.edu.vnua.fita.student.service.admin.file;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FilePathConstant;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.file.thread.*;
import vn.edu.vnua.fita.student.service.admin.iservice.IExcelService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ExcelService implements IExcelService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final PointRepository pointRepository;
    private final TermRepository termRepository;
    private final FirebaseService firebaseService;
    private final ExecutorService executor;
    private final PasswordEncoder encoder;
    private final String dataNotFound = "Không tìm thấy dữ liệu. Hãy chắc chắn rằng file excel được nhập từ ô A1";

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public List<Student> readStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> studentStrList = readExcel(file);
        List<StudentExcelData> studentExcelDataList = storeStudentData(studentStrList);
        if (!isContinue(studentExcelDataList)) {
            throw new RuntimeException(exportErrorStudentList(studentExcelDataList));
        }
        List<Student> students = new ArrayList<>();
        studentExcelDataList.forEach(studentExcelData -> students.add(studentExcelData.getStudent()));
        return students;
    }

    @Override
    public String writeStudentToExcel(List<Student> students){
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-sinh-vien");

            // Tạo hàng tiêu đề
            createStudentListHeader(sheet);

            // Sử dụng đa luồng in danh sách sinh viên ra excel
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);

                Callable<Void> callable = new WriteStudentWorker(row, student);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.STUDENT_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            // Gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.STUDENT_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

    @Override
    public List<Point> readPointFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> pointStrList = readExcel(file);
        List<PointExcelData> pointExcelDataList = storePointData(pointStrList);
        if (!isContinue(pointExcelDataList)) {
            throw new RuntimeException(exportErrorPointList(pointExcelDataList));
        }
        List<Point> points = new ArrayList<>();
        pointExcelDataList.forEach(pointExcelData -> points.add(pointExcelData.getPoint()));
        return points;
    }

    @Override
    public String writePointToExcel(List<Point> points) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-diem");

            // Tạo hàng tiêu đề
            createPointListHeader(sheet);

            // Sử dụng đa luồng in danh sách sinh viên ra excel
            int rowNum = 1;
            for (Point point : points) {
                Row row = sheet.createRow(rowNum++);

                Callable<Void> callable = new WritePointWorker(row, point);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.POINT_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            // Gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.POINT_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

    private List<String> readExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> stringList = new CopyOnWriteArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();

        // Sử dụng đa luồng đọc dữ liệu từ excel, trả về List<String>
        for (int i = 0; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (i != 0) {
                Callable<String> callable = new ReadExcelWorker(row);
                Future<String> future = executor.submit(callable);
                stringList.add(future.get());
            } else if (row == null) {
                throw new RuntimeException(dataNotFound);
            }
        }

        workbook.close();
        return stringList;
    }

    private List<StudentExcelData> storeStudentData(List<String> studentStrList) throws ExecutionException, InterruptedException {
        List<StudentExcelData> studentExcelDataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < studentStrList.size(); i++) {
            String studentStr = studentStrList.get(i);
            Callable<StudentExcelData> callable = new StoreStudentWorker(studentRepository, courseRepository, classRepository, majorRepository, encoder, studentStr, i);
            Future<StudentExcelData> future = executor.submit(callable);
            studentExcelDataList.add(future.get());
        }
        return studentExcelDataList;
    }

    private List<PointExcelData> storePointData(List<String> pointStrList) throws ExecutionException, InterruptedException {
        List<PointExcelData> studentExcelDataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < pointStrList.size(); i++) {
            String pointStr = pointStrList.get(i);
            Callable<PointExcelData> callable = new StorePointWorker(studentRepository, termRepository, pointRepository, pointStr, i);
            Future<PointExcelData> future = executor.submit(callable);
            studentExcelDataList.add(future.get());
        }
        return studentExcelDataList;
    }

    private boolean isContinue(List<? extends ExcelData> excelDataList) {
        for (ExcelData excelData :
                excelDataList) {
            if (!excelData.isValid()) {
                return false;
            }
        }
        return true;
    }

    private String exportErrorStudentList(List<StudentExcelData> studentExcelDataList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-sv-loi");

            // Tạo hàng tiêu đề
            createStudentErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (StudentExcelData studentExcelData :
                    studentExcelDataList) {
                Row row = sheet.createRow(studentExcelData.getRowIndex() + 1);
                Student student = studentExcelData.getStudent();

                Callable<Void> callable = new WriteErrorStudentWorker(row, student, cellStyle, studentExcelData);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.ERROR_STUDENT_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            //gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.ERROR_STUDENT_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

    private String exportErrorPointList(List<PointExcelData> pointExcelDataList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-diem-loi");

            // Tạo hàng tiêu đề
            createPointErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (PointExcelData pointExcelData :
                    pointExcelDataList) {
                Row row = sheet.createRow(pointExcelData.getRowIndex() + 1);
                Point point = pointExcelData.getPoint();

                Callable<Void> callable = new WriteErrorPointWorker(row, point, cellStyle, pointExcelData);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.ERROR_POINT_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            //gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.ERROR_POINT_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

    private void createStudentErrorHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Họ đệm");
        headerRow.createCell(2).setCellValue("Tên");
        headerRow.createCell(3).setCellValue("Khoá");
        headerRow.createCell(4).setCellValue("Ngành");
        headerRow.createCell(5).setCellValue("Lớp");
        headerRow.createCell(6).setCellValue("Ngày sinh");
        headerRow.createCell(7).setCellValue("Giới tính");
        headerRow.createCell(8).setCellValue("Số điện thoại");
        headerRow.createCell(9).setCellValue("Email");
        headerRow.createCell(10).setCellValue("Quê quán");
        headerRow.createCell(11).setCellValue("Ghi chú");
    }

    private void createStudentListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Họ đệm");
        headerRow.createCell(2).setCellValue("Tên");
        headerRow.createCell(3).setCellValue("Khoá");
        headerRow.createCell(4).setCellValue("Ngành");
        headerRow.createCell(5).setCellValue("Lớp");
        headerRow.createCell(6).setCellValue("Ngày sinh");
        headerRow.createCell(7).setCellValue("Giới tính");
        headerRow.createCell(8).setCellValue("Số điện thoại");
        headerRow.createCell(9).setCellValue("Email");
        headerRow.createCell(10).setCellValue("Hoàn cảnh");
        headerRow.createCell(11).setCellValue("Quê quán");
        headerRow.createCell(12).setCellValue("Nơi ở");
        headerRow.createCell(13).setCellValue("Họ tên bố");
        headerRow.createCell(14).setCellValue("Sđt bố");
        headerRow.createCell(15).setCellValue("Họ tên mẹ");
        headerRow.createCell(16).setCellValue("Sđt mẹ");
    }

    private void createPointErrorHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Học kỳ");
        headerRow.createCell(2).setCellValue("ĐTB hệ 10");
        headerRow.createCell(3).setCellValue("ĐTB hệ 4");
        headerRow.createCell(4).setCellValue("ĐRL");
        headerRow.createCell(5).setCellValue("TC tích luỹ");
        headerRow.createCell(6).setCellValue("ĐTBTL hệ 10");
        headerRow.createCell(7).setCellValue("ĐTBTL hệ 4");
        headerRow.createCell(8).setCellValue("Ghi chú");
    }

    private void createPointListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Họ đệm");
        headerRow.createCell(2).setCellValue("Tên");
        headerRow.createCell(3).setCellValue("Lớp");
        headerRow.createCell(4).setCellValue("Học kỳ");
        headerRow.createCell(5).setCellValue("ĐTB hệ 10");
        headerRow.createCell(6).setCellValue("ĐTB hệ 4");
        headerRow.createCell(7).setCellValue("ĐRL");
        headerRow.createCell(8).setCellValue("TC tích luỹ");
        headerRow.createCell(9).setCellValue("ĐTBTL hệ 10");
        headerRow.createCell(10).setCellValue("ĐTBTL hệ 4");
    }
}
