package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.StudentStatus;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomStudentStatusRepository {
    public static Specification<StudentStatus> filterStudentStatusList(String studentId,
                                                                       String statusId,
                                                                       String termId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("student").get("id"), studentId + "%"));
            }
            if(StringUtils.hasText(statusId)){
                predicates.add(criteriaBuilder.like(root.get("status").get("id"), statusId + "%"));
            }
            if(StringUtils.hasText(termId)){
                predicates.add(criteriaBuilder.like(root.get("termId"), termId));
            }
            query.orderBy(criteriaBuilder.asc(root.get("time")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
