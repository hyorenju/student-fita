package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.PointDTO;
import vn.edu.vnua.fita.student.dto.PointOfYearDTO;
import vn.edu.vnua.fita.student.dto.TrashPointDTO;
import vn.edu.vnua.fita.student.dto.TrashPointYearDTO;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.TrashPointOfYear;
import vn.edu.vnua.fita.student.request.admin.point.*;
import vn.edu.vnua.fita.student.request.admin.pointofyear.*;
import vn.edu.vnua.fita.student.service.admin.management.PointOfYearManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("admin/point-annual")
@RequiredArgsConstructor
public class PointOfYearController extends BaseController {
    private final PointOfYearManager pointOfYearManager;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_POINT_LIST', 'SUPERADMIN', 'MOD_GET_POINT_LIST')")
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
        TrashPointYearDTO response = modelMapper.map(pointOfYearManager.deletePoint(id), TrashPointYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteManyPoint(@Valid @RequestBody DeletePointYearRequest request){
        List<TrashPointYearDTO> response = pointOfYearManager.deleteManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointYearDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("delete-permanent/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deletePointPermanently(@PathVariable Long id){
        TrashPointYearDTO response = modelMapper.map(pointOfYearManager.deletePermanent(id), TrashPointYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore/{id}")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> restorePoint(@PathVariable Long id){
        TrashPointYearDTO response = modelMapper.map(pointOfYearManager.restorePoint(id), TrashPointYearDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> restoreManyPoint(@Valid @RequestBody RestorePointYearRequest request){
        List<TrashPointYearDTO> response = pointOfYearManager.restoreManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointYearDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> getTrashPoint(@Valid @RequestBody GetTrashPointYearRequest request){
        Page<TrashPointOfYear> page = pointOfYearManager.getTrashPointList(request);
        List<TrashPointYearDTO> response = page.getContent().stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointYearDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('IMPORT_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> importPointList(@RequestBody MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<PointOfYearDTO> response = pointOfYearManager.importFromExcel(file).stream().map(
                point -> modelMapper.map(point, PointOfYearDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("export")
    @PreAuthorize("hasAnyAuthority('EXPORT_POINT', 'SUPERADMIN', 'MOD_EXPORT_POINT')")
    public ResponseEntity<?> exportPointList(@RequestBody ExportPointYearListRequest request){
        String response = pointOfYearManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
