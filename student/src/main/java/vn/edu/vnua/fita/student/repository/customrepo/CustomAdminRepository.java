package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.Admin;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomAdminRepository {
    public static Specification<Admin> filterAdminList(String id) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(id)) {
                predicates.add(criteriaBuilder.like(root.get("id"), id + "%"));
            }
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
