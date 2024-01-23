package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.SchoolYear;
import vn.edu.vnua.fita.student.entity.Term;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, String> {
    SchoolYear findFirstByOrderByIdDesc();
}
