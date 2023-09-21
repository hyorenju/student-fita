package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.DropoutStatistic;
import vn.edu.vnua.fita.student.model.entity.Term;

@Repository
public interface DropoutStatisticRepository extends JpaRepository<DropoutStatistic, Integer> {
    DropoutStatistic findByTerm(Term term);
}
