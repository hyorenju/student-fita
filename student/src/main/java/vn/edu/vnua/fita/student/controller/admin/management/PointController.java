package vn.edu.vnua.fita.student.controller.admin.management;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.dto.PointDTO;
import vn.edu.vnua.fita.student.entity.dto.TrashPointDTO;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.request.admin.point.*;
import vn.edu.vnua.fita.student.service.admin.management.PointManager;

import java.util.List;

@RestController
@RequestMapping("admin/point")
@RequiredArgsConstructor
public class PointController extends BaseController {
    private final ModelMapper modelMapper;
    private final PointManager pointManager;

    @PostMapping("list")
    public ResponseEntity<?> getPointList(@Valid @RequestBody GetPointListRequest request){
        Page<Point> page = pointManager.filterPointList(request);
        List<PointDTO> response = page.getContent().stream().map(
                point -> modelMapper.map(point, PointDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createPoint(@Valid @RequestBody CreatePointRequest request){
        PointDTO response = modelMapper.map(pointManager.createPoint(request), PointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @Valid @RequestBody UpdatePointRequest request){
        PointDTO response = modelMapper.map(pointManager.updatePoint(id, request), PointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable Long id){
        TrashPointDTO response = modelMapper.map(pointManager.deletePoint(id), TrashPointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteManyPoint(@Valid @RequestBody DeletePointRequest request){
        List<TrashPointDTO> response = pointManager.deleteManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("restore/{id}")
    public ResponseEntity<?> restorePoint(@PathVariable Long id){
        TrashPointDTO response = modelMapper.map(pointManager.restorePoint(id), TrashPointDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("restore")
    public ResponseEntity<?> restoreManyPoint(@Valid @RequestBody RestorePointRequest request){
        List<TrashPointDTO> response = pointManager.restoreManyPoint(request).stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("trash")
    public ResponseEntity<?> getTrashPoint(@Valid @RequestBody GetTrashPointRequest request){
        Page<TrashPoint> page = pointManager.getTrashPointList(request);
        List<TrashPointDTO> response = page.getContent().stream().map(
                trashPoint -> modelMapper.map(trashPoint, TrashPointDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("export")
    public ResponseEntity<?> exportPointList(@RequestBody ExportPointListRequest request){
        String response = pointManager.exportToExcel(request);
        return buildItemResponse(response);
    }
}
