package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.dto.TermDTO;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;
import vn.edu.vnua.fita.student.service.admin.management.TermManager;

import java.util.List;

@RestController
@RequestMapping("admin/term")
@RequiredArgsConstructor
public class TermController extends BaseController {
    private final ModelMapper modelMapper;
    private final TermManager termManager;

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> getTermList(@RequestBody @Valid GetTermListRequest request){
        Page<Term> page = termManager.getTermList(request);
        List<TermDTO> response = page.getContent().stream().map(
                term -> modelMapper.map(term, TermDTO.class)
        ).toList();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> createTerm(@RequestBody @Valid CreateTermRequest request){
        TermDTO response = modelMapper.map(termManager.createTerm(request), TermDTO.class);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<?> deleteTerm(@PathVariable String id){
        TermDTO response = modelMapper.map(termManager.deleteTerm(id), TermDTO.class);
        return buildItemResponse(response);
    }
}
