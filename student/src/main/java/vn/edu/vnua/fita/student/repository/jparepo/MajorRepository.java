package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.Major;
import vn.edu.vnua.fita.student.model.entity.Term;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, String> {
}
