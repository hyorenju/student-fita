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
import vn.edu.vnua.fita.student.dto.DisplayDTO;
import vn.edu.vnua.fita.student.entity.Display;
import vn.edu.vnua.fita.student.request.admin.display.CreateDisplayRequest;
import vn.edu.vnua.fita.student.request.admin.display.GetDisplayListRequest;
import vn.edu.vnua.fita.student.request.admin.display.UpdateDisplayRequest;
import vn.edu.vnua.fita.student.service.admin.management.DisplayService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("admin/display")
@RequiredArgsConstructor
public class DisplayController extends BaseController {
    private final DisplayService displayService;
    private final ModelMapper modelMapper;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_DISPLAY_LIST', 'SUPERADMIN')")
    public ResponseEntity<?> getDisplayList(@Valid @RequestBody GetDisplayListRequest request){
        Page<Display> page = displayService.getDisplayList(request);
        List<DisplayDTO> response = page.getContent().stream().map(
                display -> modelMapper.map(display, DisplayDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_DISPLAY', 'SUPERADMIN')")
    public ResponseEntity<?> getDisplay(@PathVariable Long id){
        DisplayDTO response = modelMapper.map(displayService.getDisplay(id), DisplayDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_DISPLAY', 'SUPERADMIN')")
    public ResponseEntity<?> updateDisplay(@PathVariable Long id, @RequestBody @Valid UpdateDisplayRequest request) {
        DisplayDTO response = modelMapper.map(displayService.updateDisplay(id, request), DisplayDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("upload")
//    @PreAuthorize("hasAnyAuthority('UPDATE_DISPLAY', 'SUPERADMIN')")
    public ResponseEntity<?> uploadImg(@RequestBody MultipartFile file) throws IOException {
        String response = displayService.uploadImg(file);
        return buildItemResponse(response);
    }




    // Dưới đây là api for postman, đề nghị front-end dev không sử dụng dưới mọi hình thức
    @PostMapping("create")
    public ResponseEntity<?> createDisplay(@RequestBody @Valid CreateDisplayRequest request){
        DisplayDTO response = modelMapper.map(displayService.createDisplay(request), DisplayDTO.class);
        return buildItemResponse(response);
    }
}
