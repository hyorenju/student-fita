package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.repository.customrepo.CustomTermRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;
import vn.edu.vnua.fita.student.service.iservice.ITermService;

@Service
@RequiredArgsConstructor
public class TermManager implements ITermService {
    private final TermRepository termRepository;
    private final String termHadExisted = "Mã học kỳ đã tồn tại trong hệ thống";
    private final String termNotFound = "Học kỳ %s không tồn tại trong hệ thống";

    @Override
    public Page<Term> getTermList(GetTermListRequest request) {
        Specification<Term> specification = CustomTermRepository.filterTermList(
                request.getId()
        );
        return termRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Term createTerm(CreateTermRequest request) {
        if (termRepository.existsById(request.getId())) {
            throw new RuntimeException(termHadExisted);
        }

        Term term = Term.builder()
                .id(request.getId())
                .name("Học kỳ " + request.getId().charAt(4) + " - năm " + request.getId().substring(0, 4))
                .build();

        return termRepository.saveAndFlush(term);
    }

    @Override
    public Term deleteTerm(String id) {
        Term term = termRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(termNotFound, id)));
        termRepository.deleteById(id);
        return term;
    }
}
