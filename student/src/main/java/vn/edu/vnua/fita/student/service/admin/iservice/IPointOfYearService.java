package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.request.admin.pointofyear.CreatePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.DeletePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.GetPointYearListRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.UpdatePointYearRequest;

public interface IPointOfYearService {
    Page<PointOfYear> filterPointList(GetPointYearListRequest request);
    PointOfYear createPoint(CreatePointYearRequest request);
    PointOfYear updatePoint(Long id, UpdatePointYearRequest request);
    PointOfYear deletePoint(Long id);
    String deleteManyPoint(DeletePointYearRequest request);
}
