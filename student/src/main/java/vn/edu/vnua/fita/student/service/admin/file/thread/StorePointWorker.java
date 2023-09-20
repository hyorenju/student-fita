package vn.edu.vnua.fita.student.service.admin.file.thread;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Call;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.PointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StorePointWorker implements Callable<PointExcelData> {
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final PointRepository pointRepository;
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

            Point point = Point.builder()
                    .studentId(studentId)
                    .termId(termId)
                    .avgPoint10(MyUtils.parseFloatFromString(avgPoint10))
                    .avgPoint4(MyUtils.parseFloatFromString(avgPoint4))
                    .trainingPoint(MyUtils.parseIntegerFromString(trainingPoint))
                    .creditsAcc(MyUtils.parseIntegerFromString(creditsAcc))
                    .pointAcc10(MyUtils.parseFloatFromString(pointAcc10))
                    .pointAcc4(MyUtils.parseFloatFromString(pointAcc4))
                    .isDeleted(false)
                    .build();

            List<PointExcelData.ErrorDetail> errorDetailList = point.validateInformationDetailError(new CopyOnWriteArrayList<>());
            Optional<Student> studentOptional = studentRepository.findById(studentId);
            if(studentOptional.isEmpty()){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không tồn tại").build());
            } else {
                Student student = studentOptional.get();
                point.setSurname(student.getSurname());
                point.setLastName(student.getLastName());
            }
            if(!termRepository.existsById(termId)){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Học kỳ không tồn tại").build());
            }
            if(pointRepository.existsByStudentIdAndTermId(studentId,termId)){
                errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(8).errorMsg("Dữ liệu của hàng này đã tồn tại").build());
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
