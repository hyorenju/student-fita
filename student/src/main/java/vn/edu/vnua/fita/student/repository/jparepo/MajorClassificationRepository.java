package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.MajorClassification;

@Repository
public interface MajorClassificationRepository extends JpaRepository<MajorClassification, Long> {
    MajorClassification findByTermId(String termId);
}
