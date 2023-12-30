package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.entity.TrashPointOfYear;
import vn.edu.vnua.fita.student.request.admin.pointofyear.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IPointOfYearService {
    Page<PointOfYear> filterPointList(GetPointYearListRequest request);
    PointOfYear createPoint(CreatePointYearRequest request);
    PointOfYear updatePoint(Long id, UpdatePointYearRequest request);
    TrashPointOfYear deletePoint(Long id);
    List<TrashPointOfYear> deleteManyPoint(DeletePointYearRequest request);
    TrashPointOfYear deletePermanent(Long id);
    TrashPointOfYear restorePoint(Long id);
    List<TrashPointOfYear> restoreManyPoint(RestorePointYearRequest request);
    Page<TrashPointOfYear> getTrashPointList(GetTrashPointYearRequest request);
    List<PointOfYear> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String exportToExcel(ExportPointYearListRequest request);
}
