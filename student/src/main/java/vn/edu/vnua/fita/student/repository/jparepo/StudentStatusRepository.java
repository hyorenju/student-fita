package vn.edu.vnua.fita.student.repository.jparepo;

import vn.edu.vnua.fita.student.model.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentStatusRepository extends JpaRepository<StudentStatus, Long>, JpaSpecificationExecutor<StudentStatus> {
    boolean existsByStudentIdAndStatusId(String studentId, Integer statusId);
    List<StudentStatus> findAllByStudentId(String studentId);
    List<StudentStatus> findAllByStatusId(Integer statusId);
    List<StudentStatus> findAllByTermIdAndStatusId(String termId, Integer statusId);
}
