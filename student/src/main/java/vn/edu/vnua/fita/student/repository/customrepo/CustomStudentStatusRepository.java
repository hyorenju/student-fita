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
                                                                       String statusId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("studentId"), studentId + "%"));
            }
            if(StringUtils.hasText(statusId)){
                predicates.add(criteriaBuilder.like(root.get("statusId"), statusId + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
