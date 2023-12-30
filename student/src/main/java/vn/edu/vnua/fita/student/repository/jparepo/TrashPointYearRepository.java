package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.Point;
import vn.edu.vnua.fita.student.entity.PointOfYear;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.TrashPointOfYear;

@Repository
public interface TrashPointYearRepository extends JpaRepository<TrashPointOfYear, Long> {
    TrashPointOfYear findByPointOfYear(PointOfYear pointOfYear);
}
