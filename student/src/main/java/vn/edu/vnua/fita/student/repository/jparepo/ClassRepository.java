package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.AClass;
import vn.edu.vnua.fita.student.model.entity.Term;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<AClass, String>, JpaSpecificationExecutor<AClass> {
}
