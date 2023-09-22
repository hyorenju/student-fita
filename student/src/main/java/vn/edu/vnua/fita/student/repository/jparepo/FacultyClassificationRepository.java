package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.FacultyClassification;
import vn.edu.vnua.fita.student.model.entity.Term;

import java.util.List;

@Repository
public interface FacultyClassificationRepository extends JpaRepository<FacultyClassification, Integer> {
    FacultyClassification findByTerm(Term term);
    List<FacultyClassificationRepository> findAllByTermBetween(Term fromTerm, Term toTerm);
}