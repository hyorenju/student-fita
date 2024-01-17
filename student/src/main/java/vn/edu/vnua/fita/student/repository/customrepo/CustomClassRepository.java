package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.entity.AClass;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomClassRepository {
    public static Specification<AClass> filterClassList(String classId,
                                                        String monitorId,
                                                        String viceMonitorId,
                                                        String secretaryId,
                                                        String deputySecretaryId) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(classId)) {
                if (classId.contains("K\\d")) {
                    predicates.add(criteriaBuilder.like(root.get("id"), classId + "%"));
                } else {
                    predicates.add(criteriaBuilder.like(root.get("id"), "%" + classId + "%"));
                }
            }
            if (StringUtils.hasText(monitorId)) {
                predicates.add(criteriaBuilder.like(root.get("monitor").get("id"), monitorId));
            }
            if (StringUtils.hasText(viceMonitorId)) {
                predicates.add(criteriaBuilder.like(root.get("viceMonitor").get("id"), viceMonitorId));
            }
            if (StringUtils.hasText(secretaryId)) {
                predicates.add(criteriaBuilder.like(root.get("secretary").get("id"), secretaryId));
            }
            if (StringUtils.hasText(deputySecretaryId)) {
                predicates.add(criteriaBuilder.like(root.get("deputySecretary").get("id"), deputySecretaryId));
            }
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
