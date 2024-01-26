package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.FacultyClassification;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomFacultyClassificationRepository {
    public static Specification<FacultyClassification> filterFacultyInterruptClassificationList(String start,
                                                                                                String end) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (start != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("term").get("id"), start, end));
            }
            query.orderBy(criteriaBuilder.asc(root.get("term").get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<FacultyClassification> filterFacultyIOClassificationList(String start,
                                                                                         String end) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (start != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("year").get("id"), start, end));
            }
            query.orderBy(criteriaBuilder.asc(root.get("year").get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<FacultyClassification> filterFacultyCircleClassificationList(String time) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (time != null) {
                predicates.add(criteriaBuilder.like(root.get("year").get("id"), time));
            }
            query.orderBy(criteriaBuilder.asc(root.get("year").get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
