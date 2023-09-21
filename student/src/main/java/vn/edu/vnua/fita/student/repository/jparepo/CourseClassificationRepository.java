package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.CourseClassification;

@Repository
public interface CourseClassificationRepository extends JpaRepository<CourseClassification, Long> {
}
