package vn.edu.vnua.fita.student.service.admin.file.thread.point;

import lombok.AllArgsConstructor;
import vn.edu.vnua.fita.student.entity.Point;
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
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final String pointStr;
    private final int row;

    @Override
    public PointExcelData call() throws Exception {
        PointExcelData pointExcelData = new PointExcelData();

        if(!pointStr.isEmpty()){
            String[] infoList = pointStr.strip().split(",");
            String studentId = infoList[0].strip();
            String termId = infoList[1].strip();
            String avgPoint10 = infoList[2].strip();
            String avgPoint4 = infoList[3].strip();
            String trainingPoint = infoList[4].strip();
            String creditsAcc = infoList[5].strip();
            String pointAcc10 = infoList[6].strip();
            String pointAcc4 = infoList[7].strip();

            Optional<Student> studentOptional = studentRepository.findById(studentId);
            Student student = null;
            if (studentOptional.isPresent()) {
                student = studentOptional.get();
            }

            Point point = Point.builder()
                    .student(student)
                    .term(Term.builder().id(termId).build())
                    .avgPoint10(MyUtils.parseFloatFromString(avgPoint10))
                    .avgPoint4(MyUtils.parseFloatFromString(avgPoint4))
                    .trainingPoint(MyUtils.parseIntegerFromString(trainingPoint))
                    .creditsAcc(MyUtils.parseIntegerFromString(creditsAcc))
                    .pointAcc10(MyUtils.parseFloatFromString(pointAcc10))
                    .pointAcc4(MyUtils.parseFloatFromString(pointAcc4))
                    .isDeleted(false)
                    .build();

            List<PointExcelData.ErrorDetail> errorDetailList = point.validateInformationDetailError(new CopyOnWriteArrayList<>());
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            }
            if(!termRepository.existsById(termId)){
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
