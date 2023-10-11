package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.model.entity.Display;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomDisplayRepository {
    public static Specification<Display> filterDisplayList(String location) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(location)) {
                predicates.add(criteriaBuilder.like(root.get("location"), location));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
