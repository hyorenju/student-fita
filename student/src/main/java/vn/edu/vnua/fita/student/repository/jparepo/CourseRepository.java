package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.Course;
import vn.edu.vnua.fita.student.model.entity.Term;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    Course findFirstByOrderByIdDesc();
}
