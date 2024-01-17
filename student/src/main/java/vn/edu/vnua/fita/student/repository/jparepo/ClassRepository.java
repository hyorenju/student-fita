package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Student;

@Repository
public interface ClassRepository extends JpaRepository<AClass, String>, JpaSpecificationExecutor<AClass> {
    AClass findByMonitor(Student student);
    boolean existsByMonitorId(String id);
    boolean existsByViceMonitorId(String id);
    boolean existsBySecretaryId(String id);
    boolean existsByDeputySecretaryId(String id);
}
