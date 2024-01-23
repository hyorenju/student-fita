package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Status;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.request.admin.status.CreateStatusRequest;
import vn.edu.vnua.fita.student.request.admin.status.GetStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.status.UpdateStatusRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatusService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusManager implements IStatusService {
    private final StatusRepository statusRepository;
    private final String statusHadExisted = "Mã trạng thái đã tồn tại trong hệ thống";
    private final String statusNotFound = "Mã trạng thái không tồn tại trong hệ thống";
    private final String cannotDeleteDefault = "Không thể xoá trạng thái mặc định, gồm: 'Đã nhập học', 'Bị cảnh cáo', 'Đã xin thôi học', 'Bị buộc thôi học' và 'Đã tốt nghiệp'";
    private final String cannotUpdateDefault = "Không thể sửa trạng thái mặc định, gồm: 'Đã nhập học', 'Bị cảnh cáo', 'Đã xin thôi học', 'Bị buộc thôi học' và 'Đã tốt nghiệp'";
    private final String cannotDeleteAssigned = "Trạng thái này đang được gán cho sinh viên, vui lòng xoá hết các sinh viên có trạng thái này trước khi xoá trạng thái. Nếu không, có thể tiến hành cập nhật trạng thái thay vì xoá";

    @Override
    public Page<Status> getStatusList(GetStatusListRequest request) {
        return statusRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(),Sort.by("name").ascending()));
    }

    @Override
    public List<Status> getAllStatus() {
        return statusRepository.findAll(Sort.by("name").ascending());
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
        if (id == 1 || id == 2 || id == 3 || id == 4 || id == 5) {
            throw new RuntimeException(cannotUpdateDefault);
        }
        Status status = statusRepository.findById(id).orElseThrow(() -> new RuntimeException(statusNotFound));
        status.setName(request.getName());
        return statusRepository.saveAndFlush(status);
    }

    @Override
    public Status deleteStatus(Integer id) {
        try {
            if (id == 1 || id == 2 || id == 3 || id == 4 || id == 5) {
                throw new RuntimeException(cannotDeleteDefault);
            }
            Status status = statusRepository.findById(id).orElseThrow(() -> new RuntimeException(statusNotFound));
            statusRepository.deleteById(id);
            return status;
        } catch (
                DataIntegrityViolationException e) {
            throw new RuntimeException(cannotDeleteAssigned);
        }
    }
}
