package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.Display;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long>, JpaSpecificationExecutor<Display> {
}
