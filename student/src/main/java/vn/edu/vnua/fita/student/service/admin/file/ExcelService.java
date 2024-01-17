package vn.edu.vnua.fita.student.service.admin.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FilePathConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.model.file.*;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.file.thread.*;
import vn.edu.vnua.fita.student.service.admin.file.thread.aclass.StoreClassWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.aclass.WriteClassWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.aclass.WriteErrorClassWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.point.StorePointWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.point.WriteErrorPointWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.point.WritePointWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.pointannual.StorePointAnnualWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.pointannual.WriteErrorPointAnnualWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.pointannual.WritePointAnnualWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.student.StoreStudentWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.student.WriteErrorStudentWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.student.WriteStudentWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus.StoreStudentStatusWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus.WriteErrorStudentStatusWorker;
import vn.edu.vnua.fita.student.service.admin.file.thread.studentstatus.WriteStudentStatusWorker;
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
    private final PointYearRepository pointYearRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final StatusRepository statusRepository;
    private final FirebaseService firebaseService;
    private final ExecutorService executor;
    private final PasswordEncoder encoder;
    private final String dataNotFound = "Không tìm thấy dữ liệu. Hãy chắc chắn rằng file excel được nhập từ ô A1";
    private final String noData = "Tệp excel không có dữ liệu";

    @Value("${firebase.storage.bucket}")
    private String bucketName;

// Sinh viên
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

// Điểm học kỳ
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

// Điểm năm học
    @Override
    public List<PointOfYear> readPointAnnualFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> pointAnnualStrList = readExcel(file);
        List<PointAnnualExcelData> pointAnnualExcelDataList = storePointAnnualData(pointAnnualStrList);
        if (!isContinue(pointAnnualExcelDataList)) {
            throw new RuntimeException(exportErrorPointAnnualList(pointAnnualExcelDataList));
        }
        List<PointOfYear> pointOfYears = new ArrayList<>();
        pointAnnualExcelDataList.forEach(pointAnnualExcelData -> pointOfYears.add(pointAnnualExcelData.getPointOfYear()));
        return pointOfYears;
    }

    @Override
    public String writePointAnnualToExcel(List<PointOfYear> pointOfYears) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-diem-nam-hoc");

            // Tạo hàng tiêu đề
            createPointAnnualListHeader(sheet);

            // Sử dụng đa luồng in danh sách sinh viên ra excel
            int rowNum = 1;
            for (PointOfYear pointOfYear : pointOfYears) {
                Row row = sheet.createRow(rowNum++);

                Callable<Void> callable = new WritePointAnnualWorker(row, pointOfYear);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.POINT_ANNUAL_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            // Gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.POINT_ANNUAL_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

// Trạng thái sinh viên
    @Override
    public List<StudentStatus> readStudentStatusFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> studentStatusStrList = readExcel(file);
        List<StudentStatusExcelData> studentStatusExcelDataList = storeStudentStatusData(studentStatusStrList);
        if (!isContinue(studentStatusExcelDataList)) {
            throw new RuntimeException(exportErrorStudentStatusList(studentStatusExcelDataList));
        }
        List<StudentStatus> studentStatusList = new ArrayList<>();
        studentStatusExcelDataList.forEach(studentStatusExcelData -> studentStatusList.add(studentStatusExcelData.getStudentStatus()));
        return studentStatusList;
    }

    @Override
    public String writeStudentStatusToExcel(List<StudentStatus> studentStatuses) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-trang-thai-sinh-vien");

            // Tạo hàng tiêu đề
            createStudentStatusListHeader(sheet);

            // Sử dụng đa luồng in danh sách sinh viên ra excel
            int rowNum = 1;
            for (StudentStatus studentStatus : studentStatuses) {
                Row row = sheet.createRow(rowNum++);

                Callable<Void> callable = new WriteStudentStatusWorker(row, studentStatus);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.STUDENT_STATUS_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            // Gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.STUDENT_STATUS_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

// Lớp
    @Override
    public List<AClass> readClassFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> classStrList = readExcel(file);
        List<ClassExcelData> classExcelDataList = storeClassData(classStrList);
        if (!isContinue(classExcelDataList)) {
            throw new RuntimeException(exportErrorClassList(classExcelDataList));
        }
        List<AClass> classes = new ArrayList<>();
        classExcelDataList.forEach(studentStatusExcelData -> classes.add(studentStatusExcelData.getAClass()));
        return classes;
    }

    @Override
    public String writeClassListToExcel(List<AClass> classes) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-lop");

            // Tạo hàng tiêu đề
            createClassListHeader(sheet);

            // Sử dụng đa luồng in danh sách sinh viên ra excel
            int rowNum = 1;
            for (AClass aClass : classes) {
                Row row = sheet.createRow(rowNum++);

                Callable<Void> callable = new WriteClassWorker(row, aClass);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.CLASS_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            // Gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.CLASS_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file.");
        }
    }

// Đọc excel
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
            } else {
                if (row == null) {
                    throw new RuntimeException(dataNotFound);
                } else if (row.getCell(0) == null) {
                    throw new RuntimeException(dataNotFound);
                }
            }
        }

        if(totalRows==1){
            throw new RuntimeException(noData);
        }

        workbook.close();
        return stringList;
    }

// Lưu dữ liệu
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
        List<PointExcelData> pointExcelData = new CopyOnWriteArrayList<>();

        for (int i = 0; i < pointStrList.size(); i++) {
            String pointStr = pointStrList.get(i);
            Callable<PointExcelData> callable = new StorePointWorker(studentRepository, termRepository, pointStr, i);
            Future<PointExcelData> future = executor.submit(callable);
            pointExcelData.add(future.get());
        }
        return pointExcelData;
    }

    private List<PointAnnualExcelData> storePointAnnualData(List<String> pointAnnualStrList) throws ExecutionException, InterruptedException {
        List<PointAnnualExcelData> pointAnnualExcelData = new CopyOnWriteArrayList<>();

        for (int i = 0; i < pointAnnualStrList.size(); i++) {
            String pointStr = pointAnnualStrList.get(i);
            Callable<PointAnnualExcelData> callable = new StorePointAnnualWorker(studentRepository, schoolYearRepository, pointStr, i);
            Future<PointAnnualExcelData> future = executor.submit(callable);
            pointAnnualExcelData.add(future.get());
        }
        return pointAnnualExcelData;
    }

    private List<StudentStatusExcelData> storeStudentStatusData(List<String> studentStatusStrList) throws ExecutionException, InterruptedException {
        List<StudentStatusExcelData> studentStatusExcelDataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < studentStatusStrList.size(); i++) {
            String studentStatusStr = studentStatusStrList.get(i);
            Callable<StudentStatusExcelData> callable = new StoreStudentStatusWorker(studentRepository, statusRepository, studentStatusStr, i);
            Future<StudentStatusExcelData> future = executor.submit(callable);
            studentStatusExcelDataList.add(future.get());
        }
        return studentStatusExcelDataList;
    }

    private List<ClassExcelData> storeClassData(List<String> classStrList) throws ExecutionException, InterruptedException {
        List<ClassExcelData> classExcelDataList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < classStrList.size(); i++) {
            String classStr = classStrList.get(i);
            Callable<ClassExcelData> callable = new StoreClassWorker(classStr, i);
            Future<ClassExcelData> future = executor.submit(callable);
            classExcelDataList.add(future.get());
        }
        return classExcelDataList;
    }

// Kiểm tra dữ liệu lỗi
    private boolean isContinue(List<? extends ExcelData> excelDataList) {
        for (ExcelData excelData :
                excelDataList) {
            if (!excelData.isValid()) {
                return false;
            }
        }
        return true;
    }

// Xuất file lỗi
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
            throw new RuntimeException("Đã có lỗi, không thể ghi file báo lỗi dssv.");
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
            throw new RuntimeException("Đã có lỗi, không thể ghi file báo lỗi ds điểm học kỳ.");
        }
    }

    private String exportErrorPointAnnualList(List<PointAnnualExcelData> pointAnnualExcelDataList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-diem-nam-hoc-loi");

            // Tạo hàng tiêu đề
            createPointAnnualErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (PointAnnualExcelData pointAnnualExcelData :
                    pointAnnualExcelDataList) {
                Row row = sheet.createRow(pointAnnualExcelData.getRowIndex() + 1);
                PointOfYear pointOfYear = pointAnnualExcelData.getPointOfYear();

                Callable<Void> callable = new WriteErrorPointAnnualWorker(row, pointOfYear, cellStyle, pointAnnualExcelData);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.ERROR_POINT_ANNUAL_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            //gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.ERROR_POINT_ANNUAL_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file báo lỗi ds điểm năm học.");
        }
    }

    private String exportErrorStudentStatusList(List<StudentStatusExcelData> studentStatusExcelDataList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-trang-thai-sinh-vien-loi");

            // Tạo hàng tiêu đề
            createStudentStatusErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (StudentStatusExcelData studentStatusExcelData :
                    studentStatusExcelDataList) {
                Row row = sheet.createRow(studentStatusExcelData.getRowIndex() + 1);
                StudentStatus studentStatus = studentStatusExcelData.getStudentStatus();

                Callable<Void> callable = new WriteErrorStudentStatusWorker(row, studentStatus, cellStyle, studentStatusExcelData);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.ERROR_STUDENT_STATUS_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            //gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.ERROR_STUDENT_STATUS_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file báo lỗi ds trạng thái sinh viên.");
        }
    }

    private String exportErrorClassList(List<ClassExcelData> classExcelDataList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("danh-sach-lop-loi");

            // Tạo hàng tiêu đề
            createClassErrorHeader(sheet);

            // Tạo style cho error cell
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Sử dụng đa luồng in dữ liệu lỗi ra excel
            for (ClassExcelData classExcelData :
                    classExcelDataList) {
                Row row = sheet.createRow(classExcelData.getRowIndex() + 1);
                AClass aClass = classExcelData.getAClass();

                Callable<Void> callable = new WriteErrorClassWorker(row, aClass, cellStyle, classExcelData);
                Future<Void> future = executor.submit(callable);
                try {
                    future.get(); // Đợi và nhận giá trị null từ Callable
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = new FileOutputStream(FilePathConstant.ERROR_CLASS_FILE);
            workbook.write(fos);

            workbook.close();
            fos.close();

            //gọi hàm upload firebase
            return firebaseService.uploadFileExcel(FilePathConstant.ERROR_CLASS_FILE, bucketName);

        } catch (IOException e) {
            throw new RuntimeException("Đã có lỗi, không thể ghi file báo lỗi ds lớp.");
        }
    }

// Tạo tiêu đề file sinh viên
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
        headerRow.createCell(9).setCellValue("Quê quán");
        headerRow.createCell(10).setCellValue("Ghi chú");
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

// Tạo tiêu đề file điểm học kỳ
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
        headerRow.createCell(8).setCellValue("TC Đkí");
        headerRow.createCell(9).setCellValue("TC đạt");
        headerRow.createCell(10).setCellValue("TC ko đạt");
        headerRow.createCell(11).setCellValue("Ghi chú");
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
        headerRow.createCell(11).setCellValue("TC Đkí");
        headerRow.createCell(12).setCellValue("TC đạt");
        headerRow.createCell(13).setCellValue("TC ko đạt");
    }

// Tạo tiêu đề file điểm năm học
    private void createPointAnnualErrorHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Năm học");
        headerRow.createCell(2).setCellValue("ĐTB hệ 10");
        headerRow.createCell(3).setCellValue("ĐTB hệ 4");
        headerRow.createCell(4).setCellValue("ĐRL");
        headerRow.createCell(5).setCellValue("TC tích luỹ");
        headerRow.createCell(6).setCellValue("ĐTBTL hệ 10");
        headerRow.createCell(7).setCellValue("ĐTBTL hệ 4");
        headerRow.createCell(8).setCellValue("TC Đkí");
        headerRow.createCell(9).setCellValue("TC đạt");
        headerRow.createCell(10).setCellValue("TC ko đạt");
        headerRow.createCell(11).setCellValue("Ghi chú");
    }

    private void createPointAnnualListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Họ đệm");
        headerRow.createCell(2).setCellValue("Tên");
        headerRow.createCell(3).setCellValue("Lớp");
        headerRow.createCell(4).setCellValue("Năm học");
        headerRow.createCell(5).setCellValue("ĐTB hệ 10");
        headerRow.createCell(6).setCellValue("ĐTB hệ 4");
        headerRow.createCell(7).setCellValue("ĐRL");
        headerRow.createCell(8).setCellValue("TC tích luỹ");
        headerRow.createCell(9).setCellValue("ĐTBTL hệ 10");
        headerRow.createCell(10).setCellValue("ĐTBTL hệ 4");
        headerRow.createCell(11).setCellValue("TC Đkí");
        headerRow.createCell(12).setCellValue("TC đạt");
        headerRow.createCell(13).setCellValue("TC ko đạt");
    }

// Tạo tiêu đề file trạng thái sinh viên
    private void createStudentStatusErrorHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Trạng thái");
        headerRow.createCell(2).setCellValue("Thời gian");
        headerRow.createCell(3).setCellValue("Ghi chú");
    }

    private void createStudentStatusListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sinh viên");
        headerRow.createCell(1).setCellValue("Trạng thái");
        headerRow.createCell(2).setCellValue("Thời gian");
        headerRow.createCell(3).setCellValue("Học kỳ");
    }

// Tạo tiêu đề file lớp
    private void createClassErrorHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã lớp");
        headerRow.createCell(1).setCellValue("Tên lớp");
    }

    private void createClassListHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã lớp");
        headerRow.createCell(1).setCellValue("Tên lớp");
        headerRow.createCell(2).setCellValue("Msv lớp trưởng");
        headerRow.createCell(3).setCellValue("Tên lớp trưởng");
        headerRow.createCell(4).setCellValue("Sđt lớp trưởng");
        headerRow.createCell(5).setCellValue("MSV lớp phó");
        headerRow.createCell(6).setCellValue("Tên lớp phó");
        headerRow.createCell(7).setCellValue("Sđt lớp phó");
        headerRow.createCell(8).setCellValue("MSV bí thư");
        headerRow.createCell(9).setCellValue("Tên bí thư");
        headerRow.createCell(10).setCellValue("Sđt bí thư");
        headerRow.createCell(11).setCellValue("MSV phó bí thư");
        headerRow.createCell(12).setCellValue("Tên phó bí thư");
        headerRow.createCell(13).setCellValue("Sđt phó bí thư");
    }
}
