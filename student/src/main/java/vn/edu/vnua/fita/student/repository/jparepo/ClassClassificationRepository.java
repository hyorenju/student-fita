package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.ClassClassification;

@Repository
public interface ClassClassificationRepository extends JpaRepository<ClassClassification, Long>, JpaSpecificationExecutor<ClassClassification> {
    ClassClassification findByTermId(String termId);
}
