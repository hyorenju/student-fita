package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.Status;
import vn.edu.vnua.fita.student.request.admin.status.CreateStatusRequest;
import vn.edu.vnua.fita.student.request.admin.status.GetStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.status.UpdateStatusRequest;

import java.util.List;

public interface IStatusService {
    Page<Status> getStatusList(GetStatusListRequest request);
    List<Status> getAllStatus();
    Status createStatus(CreateStatusRequest request);
    Status updateStatus(Integer id, UpdateStatusRequest request);
    Status deleteStatus(Integer id);
}
