package vn.edu.vnua.fita.student.service.admin.file;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FilePathConstant;
import vn.edu.vnua.fita.student.common.ThreadsNumConstant;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.MajorRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.service.admin.file.thread.*;
import vn.edu.vnua.fita.student.service.iservice.IExcelService;

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
    private final FirebaseService firebaseService;
    private final String dataNotFound = "Không tìm thấy dữ liệu. Hãy chắc chắn rằng file excel được nhập từ ô A1";
    private final List<ExcelData> excelDataList = new CopyOnWriteArrayList<>();

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public List<Student> readStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> studentStrList = readStudentList(file);
        List<ExcelData> excelDataList = storeData(studentStrList);
        if (!isContinue(excelDataList)) {
            throw new RuntimeException(exportErrorList(excelDataList));
        }
        List<Student> students = new ArrayList<>();
        excelDataList.forEach(excelData -> students.add(excelData.getStudent()));
        return students;
    }

    @Override
    public String writeStudentToExcel(List<Student> students) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dsđtb");

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
    public String writePointToExcel(List<Point> points) {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dsđtb");

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

    private List<String> readStudentList(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);
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

    private List<ExcelData> storeData(List<String> studentStrList) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);
        List<ExcelData> excelDataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < studentStrList.size(); i++) {
            String studentStr = studentStrList.get(i);
            Callable<ExcelData> callable = new StoreDataWorker(studentRepository, courseRepository, classRepository, majorRepository, studentStr, i);
            Future<ExcelData> future = executor.submit(callable);
            excelDataList.add(future.get());
        }
        return excelDataList;
    }

    private boolean isContinue(List<ExcelData> excelDataList) {
        for (ExcelData excelData :
                excelDataList) {
            if (!excelData.isValid()) {
                return false;
            }
        }
        return true;
    }

    private String exportErrorList(List<ExcelData> excelDataList) {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dssv");

            // Tạo hàng tiêu đề
            createStudentErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (ExcelData excelData :
                    excelDataList) {
                Row row = sheet.createRow(excelData.getRowIndex() + 1);
                Student student = excelData.getStudent();

                Callable<Void> callable = new WriteErrorWorker(row, student, cellStyle, excelData);
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
        headerRow.createCell(10).setCellValue("Quê quán");
        headerRow.createCell(11).setCellValue("Nơi ở");
        headerRow.createCell(12).setCellValue("Họ tên bố");
        headerRow.createCell(13).setCellValue("Sđt bố");
        headerRow.createCell(14).setCellValue("Họ tên mẹ");
        headerRow.createCell(15).setCellValue("Sđt mẹ");
    }

    private void createPointListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Họ đệm");
        headerRow.createCell(2).setCellValue("Tên");
        headerRow.createCell(3).setCellValue("Học kỳ");
        headerRow.createCell(4).setCellValue("ĐTB hệ 10");
        headerRow.createCell(5).setCellValue("ĐTB hệ 4");
        headerRow.createCell(6).setCellValue("ĐRL");
        headerRow.createCell(7).setCellValue("TC tích luỹ");
        headerRow.createCell(8).setCellValue("ĐTBTL hệ 10");
        headerRow.createCell(9).setCellValue("ĐTBTL hệ 4");
    }
}
