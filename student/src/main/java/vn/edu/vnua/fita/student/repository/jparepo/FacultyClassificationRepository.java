package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.FacultyClassification;
import vn.edu.vnua.fita.student.entity.Term;

import java.util.List;

@Repository
public interface FacultyClassificationRepository extends JpaRepository<FacultyClassification, Integer>, JpaSpecificationExecutor<FacultyClassification> {
    FacultyClassification findByTerm(Term term);
    List<FacultyClassificationRepository> findAllByTermBetween(Term fromTerm, Term toTerm);
}
