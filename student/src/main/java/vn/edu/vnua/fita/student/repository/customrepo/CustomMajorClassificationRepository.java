package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.MajorClassification;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomMajorClassificationRepository {
    public static Specification<MajorClassification> filterMajorClassificationList(String id,
                                                                                    String start,
                                                                                    String end) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(id)) {
                predicates.add(criteriaBuilder.like(root.get("major").get("id"), id));
            }
            if (start != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("term").get("id"), start, end));
            }
            query.orderBy(criteriaBuilder.asc(root.get("term").get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
