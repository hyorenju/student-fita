package vn.edu.vnua.fita.student.repository.customrepo;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import vn.edu.vnua.fita.student.common.SortTypeConstant;
import vn.edu.vnua.fita.student.entity.PointOfYear;

import java.util.ArrayList;
import java.util.List;

public class CustomPointYearRepository {
    public static Specification<PointOfYear> filterPointYearList(String studentId,
                                                                 String year,
                                                                 String classId,
                                                                 Integer point,
                                                                 Integer accPoint,
                                                                 Integer trainingPoint,
                                                                 String sortColumn,
                                                                 String sortType) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("student").get("id"), studentId));
            }
            if (StringUtils.hasText(year)) {
                predicates.add(criteriaBuilder.like(root.get("year").get("id"), year));
            }
            if (StringUtils.hasText(classId)){
                predicates.add(criteriaBuilder.like(root.get("student").get("aclass").get("id"), classId));
            }
            if (point != null) {
                if (point == 1) {
                    predicates.add(criteriaBuilder.between(root.get("avgPoint4"), 3.6, 4.0));
                } else if (point == 2) {
                    predicates.add(criteriaBuilder.between(root.get("avgPoint4"), 3.2, 3.6));
                } else if (point == 3) {
                    predicates.add(criteriaBuilder.between(root.get("avgPoint4"), 2.5, 3.2));
                } else if (point == 4) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("avgPoint4"), 2.5));
                } else if (point == 5) {
                    predicates.add(criteriaBuilder.between(root.get("avgPoint4"), 2.0, 2.5));
                } else if (point == 6) {
                    predicates.add(criteriaBuilder.lessThan(root.get("avgPoint4"), 2.0));
                } else if (point == 7) {
                    predicates.add(criteriaBuilder.between(root.get("avgPoint4"), 1.0, 2.0));
                } else if (point == 8) {
                    predicates.add(criteriaBuilder.lessThan(root.get("avgPoint4"), 1.0));
                }
            }
            if (trainingPoint != null) {
                if (trainingPoint == 1) {
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 90, 100));
                } else if (trainingPoint == 2) {
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 80, 89));
                } else if (trainingPoint == 3) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingPoint"), 80));
                } else if (trainingPoint == 4) {
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 65, 79));
                } else if (trainingPoint == 5) {
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 50, 64));
                } else if (trainingPoint == 6) {
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 35, 49));
                } else if (trainingPoint == 7) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingPoint"), 35));
                }
            }
            if (accPoint != null) {
                if (accPoint == 1) {
                    predicates.add(criteriaBuilder.between(root.get("pointAcc4"), 3.6, 4.0));
                } else if (accPoint == 2) {
                    predicates.add(criteriaBuilder.between(root.get("pointAcc4"), 3.2, 3.6));
                } else if (accPoint == 3) {
                    predicates.add(criteriaBuilder.between(root.get("pointAcc4"), 2.5, 3.2));
                } else if (accPoint == 4) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pointAcc4"), 2.5));
                } else if (accPoint == 5) {
                    predicates.add(criteriaBuilder.between(root.get("pointAcc4"), 2.0, 2.5));
                } else if (accPoint == 6) {
                    predicates.add(criteriaBuilder.lessThan(root.get("pointAcc4"), 2.0));
                } else if (accPoint == 7) {
                    predicates.add(criteriaBuilder.between(root.get("pointAcc4"), 1.0, 2.0));
                } else if (accPoint == 8) {
                    predicates.add(criteriaBuilder.lessThan(root.get("pointAcc4"), 1.0));
                }
            }
            if (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortType)) {
                if(sortType.equals(SortTypeConstant.ASC)){
                    query.orderBy(criteriaBuilder.asc(root.get(sortColumn)));
                } else if (sortType.equals(SortTypeConstant.DESC)) {
                    query.orderBy(criteriaBuilder.desc(root.get(sortColumn)));
                }
            } else {
                query.orderBy(
                        criteriaBuilder.asc(root.get("year").get("id")),
                        criteriaBuilder.asc(root.get("student").get("lastName")),
                        criteriaBuilder.asc(root.get("student").get("surname"))
                );
            }
            predicates.add(criteriaBuilder.isFalse(root.get("isDeleted")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
