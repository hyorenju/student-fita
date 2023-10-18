package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.request.admin.point.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public interface IPointService {
    Page<Point> filterPointList(GetPointListRequest request);
    Point createPoint(CreatePointRequest request);
    Point updatePoint(Long id, UpdatePointRequest request);
    TrashPoint deletePoint(Long id);
    List<TrashPoint> deleteManyPoint(DeletePointRequest request);
    TrashPoint deletePermanent(Long id);
    TrashPoint restorePoint(Long id);
    List<TrashPoint> restoreManyPoint(RestorePointRequest request);
    Page<TrashPoint> getTrashPointList(GetTrashPointRequest request);
    List<Point> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
    String exportToExcel(ExportPointListRequest request);
}
