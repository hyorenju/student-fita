package vn.edu.vnua.fita.student.service.admin.file.thread.pointannual;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.common.AppendCharacterConstant;
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
    private final PointYearRepository pointYearRepository;
    private final StudentRepository studentRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final String pointStr;
    private final int row;

    @Override
    public PointAnnualExcelData call() throws Exception {
        PointAnnualExcelData pointAnnualExcelData = new PointAnnualExcelData();

        if(!pointStr.isEmpty()){
            String[] infoList = pointStr.strip().split(AppendCharacterConstant.APPEND_CHARACTER);
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

            Optional<Student> studentOptional = studentRepository.findById(studentId);
            Student student = Student.builder().id(studentId).build();
            if (studentOptional.isPresent()) {
                student = studentOptional.get();
            }

            Optional<SchoolYear> schoolYearOptional = schoolYearRepository.findById(yearId);
            SchoolYear schoolYear = SchoolYear.builder().id(yearId).build();
            if(schoolYearOptional.isPresent()){
                schoolYear = schoolYearOptional.get();
            }

            Optional<PointOfYear> pointOfYearOptional = pointYearRepository.findByStudentIdAndYearId(studentId, yearId);
            PointOfYear pointOfYear;
            if(pointOfYearOptional.isPresent()){
                pointOfYear = pointOfYearOptional.get();
                pointOfYear.setStudent(student);
                pointOfYear.setYear(schoolYear);
                pointOfYear.setAvgPoint10(StringUtils.hasText(avgPoint10) ? MyUtils.parseFloatFromString(avgPoint10) : null);
                pointOfYear.setAvgPoint4(StringUtils.hasText(avgPoint4) ? MyUtils.parseFloatFromString(avgPoint4) : null);
                pointOfYear.setTrainingPoint(StringUtils.hasText(trainingPoint) ? MyUtils.parseFloatFromString(trainingPoint) : null);
                pointOfYear.setCreditsAcc(StringUtils.hasText(creditsAcc) ? MyUtils.parseIntegerFromString(creditsAcc) : null);
                pointOfYear.setPointAcc10(StringUtils.hasText(pointAcc10) ? MyUtils.parseFloatFromString(pointAcc10) : null);
                pointOfYear.setPointAcc4(StringUtils.hasText(pointAcc4) ? MyUtils.parseFloatFromString(pointAcc4) : null);
                pointOfYear.setCreditsRegistered(StringUtils.hasText(creditsRegistered) ? MyUtils.parseIntegerFromString(creditsRegistered) : null);
                pointOfYear.setCreditsPassed(StringUtils.hasText(creditsPassed) ? MyUtils.parseIntegerFromString(creditsPassed) : null);
                pointOfYear.setCreditsNotPassed(StringUtils.hasText(creditsNotPassed) ? MyUtils.parseIntegerFromString(creditsNotPassed) : null);
                pointOfYear.setIsDeleted(false);
            } else {
                pointOfYear = PointOfYear.builder()
                        .student(student)
                        .year(SchoolYear.builder().id(yearId).build())
                        .avgPoint10(StringUtils.hasText(avgPoint10) ? MyUtils.parseFloatFromString(avgPoint10) : null)
                        .avgPoint4(StringUtils.hasText(avgPoint4) ? MyUtils.parseFloatFromString(avgPoint4) : null)
                        .trainingPoint(StringUtils.hasText(trainingPoint) ? MyUtils.parseFloatFromString(trainingPoint) : null)
                        .creditsAcc(StringUtils.hasText(creditsAcc) ? MyUtils.parseIntegerFromString(creditsAcc) : null)
                        .pointAcc10(StringUtils.hasText(pointAcc10) ? MyUtils.parseFloatFromString(pointAcc10) : null)
                        .pointAcc4(StringUtils.hasText(pointAcc4) ? MyUtils.parseFloatFromString(pointAcc4) : null)
                        .creditsRegistered(StringUtils.hasText(creditsRegistered) ? MyUtils.parseIntegerFromString(creditsRegistered) : null)
                        .creditsPassed(StringUtils.hasText(creditsPassed) ? MyUtils.parseIntegerFromString(creditsPassed) : null)
                        .creditsNotPassed(StringUtils.hasText(creditsNotPassed) ? MyUtils.parseIntegerFromString(creditsNotPassed) : null)
                        .isDeleted(false)
                        .build();
            }

            List<PointAnnualExcelData.ErrorDetail> errorDetailList = pointOfYear.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(schoolYearOptional.isEmpty()){
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
