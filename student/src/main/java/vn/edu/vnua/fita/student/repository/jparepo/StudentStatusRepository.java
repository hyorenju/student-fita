package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.transaction.annotation.Transactional;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentStatusRepository extends JpaRepository<StudentStatus, Long>, JpaSpecificationExecutor<StudentStatus> {
    boolean existsByStudentIdAndStatusId(String studentId, Integer statusId);
    List<StudentStatus> findAllByTermIdAndStatusId(String termId, Integer statusId);
    List<StudentStatus> findAllByStudent(Student student);
    Optional<StudentStatus> findByStudentIdAndTimeAndStatusId(String studentId, Timestamp time, Integer statusId);
}
