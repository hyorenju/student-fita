package vn.edu.vnua.fita.student.controller.visitor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.DisplayDTO;
import vn.edu.vnua.fita.student.service.visitor.DisplayService;

@RestController
@RequiredArgsConstructor
@RequestMapping("visitor")
public class DisplayController extends BaseController {
    private final DisplayService displayService;
    private final ModelMapper modelMapper;

    @PostMapping("display/{id}")
    public ResponseEntity<?> getDisplayById(@PathVariable Long id){
        DisplayDTO response = modelMapper.map(displayService.getDisplayById(id), DisplayDTO.class);
        return buildItemResponse(response);
    }

}
