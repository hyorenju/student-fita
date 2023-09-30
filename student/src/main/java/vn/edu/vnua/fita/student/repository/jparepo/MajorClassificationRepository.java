package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.MajorClassification;

@Repository
public interface MajorClassificationRepository extends JpaRepository<MajorClassification, Long>, JpaSpecificationExecutor<MajorClassification> {
    MajorClassification findByTermId(String termId);
}
