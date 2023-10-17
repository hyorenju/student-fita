package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.repository.customrepo.CustomTermRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.ITermService;

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
        return termRepository.saveAndFlush(buildTerm(request.getId()));
    }

    @Override
    public Term deleteTerm(String id) {
        Term term = termRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(termNotFound, id)));
        termRepository.deleteById(id);
        return term;
    }

    @Override
    @Scheduled(cron = "0 0 0 1 5,12 ?")
//    @Scheduled(cron = "15 * * * * ?")
    public void createTermPeriodic() {
        String termId = termRepository.findFirstByOrderByIdDesc().getId();
        int year = Integer.parseInt(termId.substring(0, termId.length() - 1));
        int term = Integer.parseInt(termId.substring(termId.length() - 1));
        if (term == 1) {
            String newTermId = year + "2";
            termRepository.saveAndFlush(buildTerm(newTermId));
        } else if (term == 2) {
            String newTermId = (year + 1) + "1";
            termRepository.saveAndFlush(buildTerm(newTermId));
        }
    }

    private Term buildTerm(String termId) {
        return Term.builder()
                .id(termId)
                .name("Học kỳ " + termId.charAt(termId.length() - 1) + " - năm " + termId.substring(0, termId.length() - 1))
                .build();
    }
}
