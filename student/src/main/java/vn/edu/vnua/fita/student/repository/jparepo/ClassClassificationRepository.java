package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.ClassClassification;

@Repository
public interface ClassClassificationRepository extends JpaRepository<ClassClassification, Long> {
}