package vn.edu.vnua.fita.student.service.admin.file.thread.pointannual;

import lombok.AllArgsConstructor;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.model.file.PointAnnualExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StorePointAnnualWorker implements Callable<PointAnnualExcelData> {
    private final StudentRepository studentRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final String pointStr;
    private final int row;

    @Override
    public PointAnnualExcelData call() throws Exception {
        PointAnnualExcelData pointAnnualExcelData = new PointAnnualExcelData();

        if(!pointStr.isEmpty()){
            String[] infoList = pointStr.strip().split(",");
            String studentId = infoList[0].strip();
            String yearId = infoList[1].strip();
            String avgPoint10 = infoList[2].strip();
            String avgPoint4 = infoList[3].strip();
            String trainingPoint = infoList[4].strip();
            String creditsAcc = infoList[5].strip();
            String pointAcc10 = infoList[6].strip();
            String pointAcc4 = infoList[7].strip();
            String creditsRegistered = infoList[8].strip();
            String creditsPassed = infoList[9].strip();
            String creditsNotPassed = infoList[10].strip();

//            Optional<Student> studentOptional = studentRepository.findById(studentId);
//            Student student = Student.builder().id(studentId).build();
//            if (studentOptional.isPresent()) {
//                student = studentOptional.get();
//            }

            PointOfYear pointOfYear = PointOfYear.builder()
                    .student(Student.builder().id(studentId).build())
                    .year(SchoolYear.builder().id(yearId).build())
                    .avgPoint10(MyUtils.parseFloatFromString(avgPoint10))
                    .avgPoint4(MyUtils.parseFloatFromString(avgPoint4))
                    .trainingPoint(MyUtils.parseIntegerFromString(trainingPoint))
                    .creditsAcc(MyUtils.parseIntegerFromString(creditsAcc))
                    .pointAcc10(MyUtils.parseFloatFromString(pointAcc10))
                    .pointAcc4(MyUtils.parseFloatFromString(pointAcc4))
                    .creditsRegistered(MyUtils.parseIntegerFromString(creditsRegistered))
                    .creditsPassed(MyUtils.parseIntegerFromString(creditsPassed))
                    .creditsNotPassed(MyUtils.parseIntegerFromString(creditsNotPassed))
                    .isDeleted(false)
                    .build();

            List<PointAnnualExcelData.ErrorDetail> errorDetailList = pointOfYear.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(!studentRepository.existsById(studentId)){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(!schoolYearRepository.existsById(yearId)){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Năm học không tồn tại").build());
            }

            pointAnnualExcelData.setPointOfYear(pointOfYear);
            if (!errorDetailList.isEmpty()) {
                pointAnnualExcelData.setErrorDetailList(errorDetailList);
                pointAnnualExcelData.setValid(false);
            }
            pointAnnualExcelData.setRowIndex(row);
        }

        return pointAnnualExcelData;
    }
}
