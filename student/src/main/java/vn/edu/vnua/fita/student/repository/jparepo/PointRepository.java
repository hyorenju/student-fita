package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, JpaSpecificationExecutor<Point> {
    boolean existsByStudentIdAndTermId(String studentId, String termId);
    Optional<Point> findByStudentIdAndTermId(String studentId, String termId);
    List<Point> findAllByStudentIdOrderByTermId(String studentId);
    List<Point> findAllByStudent(Student student);
}
