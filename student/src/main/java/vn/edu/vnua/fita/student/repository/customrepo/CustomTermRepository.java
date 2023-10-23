package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.Term;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomTermRepository {
    public static Specification<Term> filterTermList(String termId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(termId)) {
                predicates.add(criteriaBuilder.like(root.get("id"), termId+"%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
