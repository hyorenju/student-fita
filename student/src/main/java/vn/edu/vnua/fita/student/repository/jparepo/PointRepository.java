package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.Point;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>, JpaSpecificationExecutor<Point> {
    boolean existsByStudentIdAndTermId(String studentId, String termId);
    List<Point> findAllByStudentId(String studentId);
    Point findByStudentIdAndTermId(String studentId, String termId);
}
