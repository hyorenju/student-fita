package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;

import java.util.List;

public interface ITermService {
    Page<Term> getTermList(GetTermListRequest request);
    List<Term> getAllTerm();
    Term createTerm(CreateTermRequest request);
    Term deleteTerm(String id);
    void createTermPeriodic();
}
