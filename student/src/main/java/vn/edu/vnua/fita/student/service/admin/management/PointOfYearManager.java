package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.repository.customrepo.CustomPointYearRepository;
import vn.edu.vnua.fita.student.repository.jparepo.PointYearRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.admin.pointofyear.CreatePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.DeletePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.GetPointYearListRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.UpdatePointYearRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IPointOfYearService;

@Service
@RequiredArgsConstructor
public class PointOfYearManager implements IPointOfYearService {
    private final PointYearRepository pointYearRepository;
    private final StudentRepository studentRepository;
    private final String pointHadExistedMsg = "Dữ liệu của sinh viên %s trong năm học %s đã tồn tại trong hệ thống";
    private final String studentNotFoundMsg = "Mã sinh viên %s không tồn tại trong hệ thống";
    private final String pointNotFoundMsg = "Không tìm thấy mã điểm %d";

    @Override
    public Page<PointOfYear> filterPointList(GetPointYearListRequest request) {

        Specification<PointOfYear> specification = CustomPointYearRepository.filterPointYearList(
                request.getStudentId(),
                request.getYear(),
                request.getClassId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint(),
                request.getSort().getSortColumn(),
                request.getSort().getSortType()
        );
        return pointYearRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public PointOfYear createPoint(CreatePointYearRequest request) {
        String studentId = request.getStudent().getId();
        String year = request.getYear();
        if (pointYearRepository.existsByStudentIdAndYear(studentId, year)) {
            throw new RuntimeException(String.format(pointHadExistedMsg, studentId, year));
        }
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));
        PointOfYear point = PointOfYear.builder()
                .student(student)
                .year(year)
                .avgPoint10(request.getAvgPoint10())
                .avgPoint4(request.getAvgPoint4())
                .trainingPoint(request.getTrainingPoint())
                .creditsAcc(request.getCreditsAcc())
                .pointAcc10(request.getPointAcc10())
                .pointAcc4(request.getPointAcc4())
                .creditsRegistered(request.getCreditsRegistered())
                .creditsPassed(request.getCreditsPassed())
                .creditsNotPassed(request.getCreditsNotPassed())
                .isDeleted(false)
                .build();

        return pointYearRepository.saveAndFlush(point);
    }

    @Override
    public PointOfYear updatePoint(Long id, UpdatePointYearRequest request) {
        String studentId = request.getStudent().getId();
        String year = request.getYear();
        PointOfYear point = pointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointHadExistedMsg, studentId, year)));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));

        point.setStudent(student);
        point.setYear(year);
        point.setAvgPoint10(request.getAvgPoint10());
        point.setAvgPoint4(request.getAvgPoint4());
        point.setTrainingPoint(request.getTrainingPoint());
        point.setCreditsAcc(request.getCreditsAcc());
        point.setPointAcc10(request.getPointAcc10());
        point.setPointAcc4(request.getPointAcc4());
        point.setCreditsRegistered(request.getCreditsRegistered());
        point.setCreditsPassed(request.getCreditsPassed());
        point.setCreditsNotPassed(request.getCreditsNotPassed());

        return pointYearRepository.saveAndFlush(point);
    }

    @Override
    public PointOfYear deletePoint(Long id) {
        PointOfYear point = pointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
        pointYearRepository.delete(point);
        return point;
    }

    @Override
    public String deleteManyPoint(DeletePointYearRequest request) {
        try {
            pointYearRepository.deleteAllById(request.getIds());
            return "Xoá thành công";
        } catch (Exception e){
            throw new RuntimeException("Có lỗi trong việc xoá danh sách điểm");
        }
    }
}
