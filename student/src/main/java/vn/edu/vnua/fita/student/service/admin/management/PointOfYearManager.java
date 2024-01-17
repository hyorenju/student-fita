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
import vn.edu.vnua.fita.student.repository.customrepo.CustomPointYearRepository;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.point.DeletePointRequest;
import vn.edu.vnua.fita.student.request.admin.point.ExportPointListRequest;
import vn.edu.vnua.fita.student.request.admin.point.GetTrashPointRequest;
import vn.edu.vnua.fita.student.request.admin.point.RestorePointRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.*;
import vn.edu.vnua.fita.student.service.admin.file.ExcelService;
import vn.edu.vnua.fita.student.service.admin.iservice.IPointOfYearService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PointOfYearManager implements IPointOfYearService {
    private final PointYearRepository pointYearRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final TrashPointYearRepository trashPointYearRepository;
    private final ExcelService excelService;
    private final String pointHadExistedMsg = "Dữ liệu của sinh viên %s trong học kỳ %s đã tồn tại trong hệ thống";
    private final String studentNotFoundMsg = "Mã sinh viên %s không tồn tại trong hệ thống";
    private final String yearNotFoundMsg = "Năm học %s không tồn tại trong hệ thống";
    private final String pointNotFoundMsg = "Không tìm thấy mã điểm %d";
    private final String byWhomNotFound = "Không thể xác định danh tính người xoá";
    private final String trashNotFound = "Không tìm thấy rác";

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
        String yearId = request.getYear().getId();
        if (pointYearRepository.existsByStudentIdAndYearId(studentId, yearId)) {
            throw new RuntimeException(String.format(pointHadExistedMsg, studentId, yearId));
        }
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));
        SchoolYear year = schoolYearRepository.findById(yearId).orElseThrow(() -> new RuntimeException(String.format(yearNotFoundMsg, yearId)));
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
        String yearId = request.getYear().getId();
        PointOfYear point = pointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointHadExistedMsg, studentId, yearId)));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(String.format(studentNotFoundMsg, studentId)));
        SchoolYear year = schoolYearRepository.findById(yearId).orElseThrow(() -> new RuntimeException(String.format(yearNotFoundMsg, yearId)));

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
    public TrashPointOfYear deletePoint(Long id) {
        PointOfYear point = pointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
        point.setIsDeleted(true);
        TrashPointOfYear trashPoint = moveToTrash(point);
//        pointRepository.saveAndFlush(point);
        return trashPoint;
    }

    @Override
    public List<TrashPointOfYear> deleteManyPoint(DeletePointYearRequest request) {
        List<PointOfYear> pointOfYears = checkIds(request.getIds());
        List<TrashPointOfYear> trashPointOfYears = new ArrayList<>();
        pointOfYears.forEach(point -> {
            point.setIsDeleted(true);
//            pointRepository.saveAndFlush(point);
            trashPointOfYears.add(moveToTrash(point));
        });
        return trashPointOfYears;
    }

    @Override
    public TrashPointOfYear deletePermanent(Long id) {
        TrashPointOfYear trashPointOfYear = trashPointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(trashNotFound));
        PointOfYear pointOfYear = trashPointOfYear.getPointOfYear();

        trashPointYearRepository.delete(trashPointOfYear);

        pointYearRepository.delete(pointOfYear);

        return trashPointOfYear;
    }

    @Override
    public TrashPointOfYear restorePoint(Long id) {
        TrashPointOfYear trashPointOfYear = trashPointYearRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        PointOfYear pointOfYear = trashPointOfYear.getPointOfYear();
        pointOfYear.setIsDeleted(false);
        restoreFromTrash(pointOfYear);
//        pointRepository.saveAndFlush(point);
        return trashPointOfYear;
    }

    @Override
    public List<TrashPointOfYear> restoreManyPoint(RestorePointYearRequest request) {
        List<TrashPointOfYear> trashPointOfYears = trashPointYearRepository.findAllById(request.getIds());
        trashPointOfYears.forEach(trashPointOfYear -> {
            PointOfYear pointOfYear = trashPointOfYear.getPointOfYear();
            pointOfYear.setIsDeleted(false);
            restoreFromTrash(pointOfYear);
//            pointRepository.saveAndFlush(point);
        });
        return trashPointOfYears;
    }

    @Override
    public Page<TrashPointOfYear> getTrashPointList(GetTrashPointYearRequest request) {
        return trashPointYearRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public List<PointOfYear> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return pointYearRepository.saveAllAndFlush(excelService.readPointAnnualFromExcel(file));
    }

    @Override
    public String exportToExcel(ExportPointYearListRequest request) {
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
        List<PointOfYear> pointOfYears = pointYearRepository.findAll(specification);
        return excelService.writePointAnnualToExcel(pointOfYears);
    }

    private TrashPointOfYear moveToTrash(PointOfYear pointOfYear) {
        Admin admin = findAdminDeletedIt();

        TrashPointOfYear trashPointOfYear = TrashPointOfYear.builder()
                .pointOfYear(pointOfYear)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .deletedBy(admin)
                .build();
        trashPointYearRepository.saveAndFlush(trashPointOfYear);
        return trashPointOfYear;
    }

    private void restoreFromTrash(PointOfYear pointOfYear) {
        TrashPointOfYear trashPointOfYear = trashPointYearRepository.findByPointOfYear(pointOfYear);
        trashPointYearRepository.deleteById(trashPointOfYear.getId());
    }

    private List<PointOfYear> checkIds(List<Long> ids) {
        List<PointOfYear> pointOfYears = new ArrayList<>();
        ids.forEach(id -> {
            PointOfYear pointOfYear = pointYearRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(pointNotFoundMsg, id)));
            pointOfYears.add(pointOfYear);
        });
        return pointOfYears;
    }

    private Admin findAdminDeletedIt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(byWhomNotFound));
    }
}
