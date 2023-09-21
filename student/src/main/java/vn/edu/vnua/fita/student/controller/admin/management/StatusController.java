package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.dto.StatusDTO;
import vn.edu.vnua.fita.student.model.entity.Status;
import vn.edu.vnua.fita.student.request.admin.status.CreateStatusRequest;
import vn.edu.vnua.fita.student.request.admin.status.GetStatusListRequest;
import vn.edu.vnua.fita.student.request.admin.status.UpdateStatusRequest;
import vn.edu.vnua.fita.student.service.admin.management.StatusManager;

import java.util.List;

@RestController
@RequestMapping("admin/status")
@RequiredArgsConstructor
public class StatusController extends BaseController {
    private final StatusManager statusManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    public ResponseEntity<?> getStatusList(@Valid @RequestBody GetStatusListRequest request){
        Page<Status> page = statusManager.getStatusList(request);
        List<StatusDTO> response = page.getContent().stream().map(
                status -> modelMapper.map(status, StatusDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createStatus(@Valid @RequestBody CreateStatusRequest request){
        StatusDTO response = modelMapper.map(statusManager.createStatus(request), StatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody UpdateStatusRequest request, @PathVariable Integer id){
        StatusDTO response = modelMapper.map(statusManager.updateStatus(id, request), StatusDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Integer id){
        StatusDTO response = modelMapper.map(statusManager.deleteStatus(id), StatusDTO.class);
        return buildItemResponse(response);
    }
}
