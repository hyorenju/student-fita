package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.model.entity.Course;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCourseRepository {
    public static Specification<Course> filterCourseList(String courseId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(courseId)) {
                predicates.add(criteriaBuilder.like(root.get("id"), courseId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
