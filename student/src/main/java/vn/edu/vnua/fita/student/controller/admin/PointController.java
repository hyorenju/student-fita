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
import vn.edu.vnua.fita.student.dto.TrashPointDTO;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.request.admin.point.*;
import vn.edu.vnua.fita.student.service.admin.management.PointManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("admin/point")
@RequiredArgsConstructor
public class PointController extends BaseController {
    private final ModelMapper modelMapper;
    private final PointManager pointManager;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_POINT_LIST', 'SUPERADMIN')")
    public ResponseEntity<?> getPointList(@Valid @RequestBody GetPointListRequest request){
        Page<Point> page = pointManager.filterPointList(request);
        List<PointDTO> response = page.getContent().stream().map(
                point -> modelMapper.map(point, PointDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> createPoint(@Valid @RequestBody CreatePointRequest request){
        PointDTO response = modelMapper.map(pointManager.createPoint(request), PointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @Valid @RequestBody UpdatePointRequest request){
        PointDTO response = modelMapper.map(pointManager.updatePoint(id, request), PointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deletePoint(@PathVariable Long id){
        TrashPointDTO response = modelMapper.map(pointManager.deletePoint(id), TrashPointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    @PreAuthorize("hasAnyAuthority('DELETE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> deleteManyPoint(@Valid @RequestBody DeletePointRequest request){
        List<TrashPointDTO> response = pointManager.deleteManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("restore/{id}")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> restorePoint(@PathVariable Long id){
        TrashPointDTO response = modelMapper.map(pointManager.restorePoint(id), TrashPointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> restoreManyPoint(@Valid @RequestBody RestorePointRequest request){
        List<TrashPointDTO> response = pointManager.restoreManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    @PreAuthorize("hasAnyAuthority('RESTORE_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> getTrashPoint(@Valid @RequestBody GetTrashPointRequest request){
        Page<TrashPoint> page = pointManager.getTrashPointList(request);
        List<TrashPointDTO> response = page.getContent().stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("import")
    @PreAuthorize("hasAnyAuthority('IMPORT_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> importPointList(@RequestBody MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<PointDTO> response = pointManager.importFromExcel(file).stream().map(
                point -> modelMapper.map(point, PointDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("export")
    @PreAuthorize("hasAnyAuthority('EXPORT_POINT', 'SUPERADMIN')")
    public ResponseEntity<?> exportPointList(@RequestBody ExportPointListRequest request){
        String response = pointManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
