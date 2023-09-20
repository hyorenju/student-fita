package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
}
