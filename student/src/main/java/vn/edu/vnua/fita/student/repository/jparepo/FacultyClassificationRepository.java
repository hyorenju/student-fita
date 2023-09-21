package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.FacultyClassification;
import vn.edu.vnua.fita.student.model.entity.Term;

@Repository
public interface FacultyClassificationRepository extends JpaRepository<FacultyClassification, Integer> {
    FacultyClassification findByTerm(Term term);
}
