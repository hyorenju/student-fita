package vn.edu.vnua.fita.student.service.admin.file.thread.point;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.common.AppendCharacterConstant;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.SchoolYear;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.PointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StorePointWorker implements Callable<PointExcelData> {
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final String pointStr;
    private final int row;

    @Override
    public PointExcelData call() throws Exception {
        PointExcelData pointExcelData = new PointExcelData();

        if(!pointStr.isEmpty()){
            String[] infoList = pointStr.strip().split(AppendCharacterConstant.APPEND_CHARACTER);
            String studentId = infoList[0].strip();
            String termId = infoList[1].strip();
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

            Optional<Term> termOptional = termRepository.findById(termId);
            Term term = Term.builder().id(termId).build();
            if(termOptional.isPresent()){
                term = termOptional.get();
            }

            Optional<Point> pointOptional = pointRepository.findByStudentIdAndTermId(studentId, termId);
            Point point;
            if(pointOptional.isPresent()){
                point = pointOptional.get();
                point.setStudent(student);
                point.setTerm(term);
                point.setAvgPoint10(StringUtils.hasText(avgPoint10) ? MyUtils.parseFloatFromString(avgPoint10) : null);
                point.setAvgPoint4(StringUtils.hasText(avgPoint4) ? MyUtils.parseFloatFromString(avgPoint4) : null);
                point.setTrainingPoint(StringUtils.hasText(trainingPoint) ? MyUtils.parseIntegerFromString(trainingPoint) : null);
                point.setCreditsAcc(StringUtils.hasText(creditsAcc) ? MyUtils.parseIntegerFromString(creditsAcc) : null);
                point.setPointAcc10(StringUtils.hasText(pointAcc10) ? MyUtils.parseFloatFromString(pointAcc10) : null);
                point.setPointAcc4(StringUtils.hasText(pointAcc4) ? MyUtils.parseFloatFromString(pointAcc4) : null);
                point.setCreditsRegistered(StringUtils.hasText(creditsRegistered) ? MyUtils.parseIntegerFromString(creditsRegistered) : null);
                point.setCreditsPassed(StringUtils.hasText(creditsPassed) ? MyUtils.parseIntegerFromString(creditsPassed) : null);
                point.setCreditsNotPassed(StringUtils.hasText(creditsNotPassed) ? MyUtils.parseIntegerFromString(creditsNotPassed) : null);
                point.setIsDeleted(false);
            } else {
                point = Point.builder()
                        .student(student)
                        .term(Term.builder().id(termId).build())
                        .avgPoint10(StringUtils.hasText(avgPoint10) ? MyUtils.parseFloatFromString(avgPoint10) : null)
                        .avgPoint4(StringUtils.hasText(avgPoint4) ? MyUtils.parseFloatFromString(avgPoint4) : null)
                        .trainingPoint(StringUtils.hasText(trainingPoint) ? MyUtils.parseIntegerFromString(trainingPoint) : null)
                        .creditsAcc(StringUtils.hasText(creditsAcc) ? MyUtils.parseIntegerFromString(creditsAcc) : null)
                        .pointAcc10(StringUtils.hasText(pointAcc10) ? MyUtils.parseFloatFromString(pointAcc10) : null)
                        .pointAcc4(StringUtils.hasText(pointAcc4) ? MyUtils.parseFloatFromString(pointAcc4) : null)
                        .creditsRegistered(StringUtils.hasText(creditsRegistered) ? MyUtils.parseIntegerFromString(creditsRegistered) : null)
                        .creditsPassed(StringUtils.hasText(creditsPassed) ? MyUtils.parseIntegerFromString(creditsPassed) : null)
                        .creditsNotPassed(StringUtils.hasText(creditsNotPassed) ? MyUtils.parseIntegerFromString(creditsNotPassed) : null)
                        .isDeleted(false)
                        .build();
            }

            List<PointExcelData.ErrorDetail> errorDetailList = point.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(termOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Học kỳ không tồn tại").build());
            }

            pointExcelData.setPoint(point);
            if (!errorDetailList.isEmpty()) {
                pointExcelData.setErrorDetailList(errorDetailList);
                pointExcelData.setValid(false);
            }
            pointExcelData.setRowIndex(row);
        }

        return pointExcelData;
    }
}
