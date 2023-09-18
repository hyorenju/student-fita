package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.request.admin.point.*;

import java.util.List;


public interface IPointService {
    Page<Point> filterPointList(GetPointListRequest request);
    Point createPoint(CreatePointRequest request);
    Point updatePoint(Long id, UpdatePointRequest request);
    TrashPoint deletePoint(Long id);
    List<TrashPoint> deleteManyPoint(DeletePointRequest request);
    TrashPoint restorePoint(Long id);
    List<TrashPoint> restoreManyPoint(RestorePointRequest request);
    Page<TrashPoint> getTrashPointList(GetTrashPointRequest request);
    String exportToExcel(ExportPointListRequest request);
}
