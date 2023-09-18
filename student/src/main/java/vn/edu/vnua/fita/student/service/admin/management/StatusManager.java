package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Status;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.request.admin.status.CreateStatusRequest;
import vn.edu.vnua.fita.student.request.admin.status.GetStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.status.UpdateStatusRequest;
import vn.edu.vnua.fita.student.service.iservice.IStatusService;

@Service
@RequiredArgsConstructor
public class StatusManager implements IStatusService {
    private final StatusRepository statusRepository;
    private final String statusHadExisted = "Mã trạng thái đã tồn tại trong hệ thống";
    private final String statusNotFound = "Mã trạng thái không tồn tại trong hệ thống";
    private final String cannotDelete = "Không thể xoá trạng thái này";

    @Override
    public Page<Status> getStatusList(GetStatusListRequest request) {
        return statusRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize()));
    }

    @Override
    public Status createStatus(CreateStatusRequest request) {
        Status status = Status.builder()
                .name(request.getName())
                .build();
        return statusRepository.saveAndFlush(status);
    }

    @Override
    public Status updateStatus(Integer id, UpdateStatusRequest request) {
        Status status = statusRepository.findById(id).orElseThrow(() -> new RuntimeException(statusNotFound));
        status.setName(request.getName());
        return statusRepository.saveAndFlush(status);
    }

    @Override
    public Status deleteStatus(Integer id) {
        if(id==1){
            throw new RuntimeException(cannotDelete);
        }
        Status status = statusRepository.findById(id).orElseThrow(() -> new RuntimeException(statusNotFound));
        statusRepository.deleteById(id);
        return status;
    }
}
