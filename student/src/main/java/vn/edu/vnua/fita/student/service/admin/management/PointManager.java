package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.repository.customrepo.CustomPointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.point.*;
import vn.edu.vnua.fita.student.service.admin.file.ExcelService;
import vn.edu.vnua.fita.student.service.admin.iservice.IPointService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PointManager implements IPointService {
    private final PointRepository pointRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final TermRepository termRepository;
    private final TrashPointRepository trashPointRepository;
    private final ExcelService excelService;
    private final String pointHadExistedMsg = "Dữ liệu của sinh viên %s trong học kỳ %s đã tồn tại trong hệ thống";
    private final String studentNotFoundMsg = "Mã sinh viên %s không tồn tại trong hệ thống";
    private final String termNotFoundMsg = "Học kỳ %s không tồn tại trong hệ thống";
    private final String pointNotFoundMsg = "Không tìm thấy mã điểm %d";
    private final String byWhomNotFound = "Không thể xác định danh tính người xoá";
    private final String trashNotFound = "Không tìm thấy rác";

    @Override
    public Page<Point> filterPointList(GetPointListRequest request) {
        Specification<Point> specification = CustomPointRepository.filterPointList(
                request.getStudentId(),
                request.getFilter().getTermId(),
                request.getFilter().getClassId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint(),
                request.getSort().getSortColumn(),
                request.getSort().getSortType()
        );
        return pointRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Point createPoint(CreatePointRequest request) {
        String studentId = request.getStudent().getId();
        String termId = request.getTerm().getId();
        if (pointRepository.existsByStudentIdAndTermId(studentId, termId)) {
            throw new RuntimeException(String.format(pointHadExistedMsg, studentId, termId));
        }
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));
        Term term = termRepository.findById(termId).orElseThrow(() -> new RuntimeException(String.format(termNotFoundMsg, termId)));
        Point point = Point.builder()
                .student(student)
                .term(term)
                .avgPoint10(request.getAvgPoint10())
                .avgPoint4(request.getAvgPoint4())
                .trainingPoint(request.getTrainingPoint())
                .creditsAcc(request.getCreditsAcc())
                .pointAcc10(request.getPointAcc10())
                .pointAcc4(request.getPointAcc4())
                .isDeleted(false)
                .build();

        return pointRepository.saveAndFlush(point);
    }

    @Override
    public Point updatePoint(Long id, UpdatePointRequest request) {
        String studentId = request.getStudent().getId();
        String termId = request.getTerm().getId();
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointHadExistedMsg, studentId, termId)));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));
        Term term = termRepository.findById(termId).orElseThrow(() -> new RuntimeException(String.format(termNotFoundMsg, termId)));

        point.setStudent(student);
        point.setTerm(term);
        point.setAvgPoint10(request.getAvgPoint10());
        point.setAvgPoint4(request.getAvgPoint4());
        point.setTrainingPoint(request.getTrainingPoint());
        point.setCreditsAcc(request.getCreditsAcc());
        point.setPointAcc10(request.getPointAcc10());
        point.setPointAcc4(request.getPointAcc4());

        return pointRepository.saveAndFlush(point);
    }

    @Override
    public TrashPoint deletePoint(Long id) {
        Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
        point.setIsDeleted(true);
        TrashPoint trashPoint = moveToTrash(point);
        pointRepository.saveAndFlush(point);
        return trashPoint;
    }

    @Override
    public List<TrashPoint> deleteManyPoint(DeletePointRequest request) {
        List<Point> points = checkIds(request.getIds());
        List<TrashPoint> trashPoints = new ArrayList<>();
        points.forEach(point -> {
            point.setIsDeleted(true);
            pointRepository.saveAndFlush(point);
            trashPoints.add(moveToTrash(point));
        });
        return trashPoints;
    }

    @Override
    public TrashPoint deletePermanent(Long id) {
        TrashPoint trashPoint = trashPointRepository.findById(id).orElseThrow(() -> new RuntimeException(trashNotFound));
        Point point = trashPoint.getPoint();

        trashPointRepository.delete(trashPoint);

        pointRepository.delete(point);

        return trashPoint;
    }

    @Override
    public TrashPoint restorePoint(Long id) {
        TrashPoint trashPoint = trashPointRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        Point point = trashPoint.getPoint();
        point.setIsDeleted(false);
        restoreFromTrash(point);
        pointRepository.saveAndFlush(point);
        return trashPoint;
    }

    @Override
    public List<TrashPoint> restoreManyPoint(RestorePointRequest request) {
        List<TrashPoint> trashPoints = trashPointRepository.findAllById(request.getIds());
        trashPoints.forEach(trashPoint -> {
            Point point = trashPoint.getPoint();
            point.setIsDeleted(false);
            restoreFromTrash(point);
            pointRepository.saveAndFlush(point);
        });
        return trashPoints;
    }

    @Override
    public Page<TrashPoint> getTrashPointList(GetTrashPointRequest request) {
        return trashPointRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public List<Point> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return pointRepository.saveAllAndFlush(excelService.readPointFromExcel(file));
    }

    @Override
    public String exportToExcel(ExportPointListRequest request) {
        Specification<Point> specification = CustomPointRepository.filterPointList(
                request.getStudentId(),
                request.getFilter().getTermId(),
                request.getFilter().getClassId(),
                request.getFilter().getPoint(),
                request.getFilter().getAccPoint(),
                request.getFilter().getTrainingPoint(),
                request.getSort().getSortColumn(),
                request.getSort().getSortType()
        );
        List<Point> points = pointRepository.findAll(specification);
        return excelService.writePointToExcel(points);
    }

    private TrashPoint moveToTrash(Point point) {
        Admin admin = findAdminDeletedIt();

        TrashPoint trashPoint = TrashPoint.builder()
                .point(point)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .deletedBy(admin)
                .build();
        trashPointRepository.saveAndFlush(trashPoint);
        return trashPoint;
    }

    private void restoreFromTrash(Point point) {
        TrashPoint trashPoint = trashPointRepository.findByPoint(point);
        trashPointRepository.deleteById(trashPoint.getId());
    }

    private List<Point> checkIds(List<Long> ids) {
        List<Point> points = new ArrayList<>();
        ids.forEach(id -> {
            Point point = pointRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
            points.add(point);
        });
        return points;
    }

    private Admin findAdminDeletedIt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(byWhomNotFound));
    }
}
