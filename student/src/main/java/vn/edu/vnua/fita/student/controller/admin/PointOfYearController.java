package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.PointOfYearDTO;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.request.admin.pointofyear.CreatePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.DeletePointYearRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.GetPointYearListRequest;
import vn.edu.vnua.fita.student.request.admin.pointofyear.UpdatePointYearRequest;
import vn.edu.vnua.fita.student.service.admin.management.PointOfYearManager;

import java.util.List;

@RestController
@RequestMapping("admin/point-annual")
@RequiredArgsConstructor
public class PointOfYearController extends BaseController {
    private final PointOfYearManager pointOfYearManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_POINT_LIST', 'SUPERADMIN')")
    public ResponseEntity<?> getPointList(@Valid @RequestBody GetPointYearListRequest request){
        Page<PointOfYear> page = pointOfYearManager.filterPointList(request);
        List<PointOfYearDTO> response = page.getContent().stream().map(
                point -> modelMapper.map(point, PointOfYearDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> createPoint(@Valid @RequestBody CreatePointYearRequest request){
        PointOfYearDTO response = modelMapper.map(pointOfYearManager.createPoint(request), PointOfYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @Valid @RequestBody UpdatePointYearRequest request){
        PointOfYearDTO response = modelMapper.map(pointOfYearManager.updatePoint(id, request), PointOfYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deletePoint(@PathVariable Long id){
        PointOfYearDTO response = modelMapper.map(pointOfYearManager.deletePoint(id), PointOfYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteManyPoint(@Valid @RequestBody DeletePointYearRequest request){
        String response = pointOfYearManager.deleteManyPoint(request);
        return buildItemResponse(response);
    }
}
