package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.PointOfYear;

import java.util.Optional;

@Repository
public interface PointYearRepository extends JpaRepository<PointOfYear, Long>, JpaSpecificationExecutor<PointOfYear> {
    boolean existsByStudentIdAndYearId(String studentId, String year);
    Optional<PointOfYear> findByStudentIdAndYearId(String studentId, String yearId);
}
