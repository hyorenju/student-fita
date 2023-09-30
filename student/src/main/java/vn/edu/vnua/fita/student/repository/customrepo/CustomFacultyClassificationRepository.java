package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.model.entity.FacultyClassification;
import vn.edu.vnua.fita.student.model.entity.MajorClassification;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomFacultyClassificationRepository {
    public static Specification<FacultyClassification> filterFacultyClassificationList(String start,
                                                                                     String end) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (start != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("term").get("id"), start, end));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
