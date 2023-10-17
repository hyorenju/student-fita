package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.TrashPoint;
import vn.edu.vnua.fita.student.entity.Point;

@Repository
public interface TrashPointRepository extends JpaRepository<TrashPoint, Long> {
    TrashPoint findByPoint(Point point);
}
