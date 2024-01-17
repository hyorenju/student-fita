package vn.edu.vnua.fita.student.repository.jparepo;

import vn.edu.vnua.fita.student.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    List<Student> findAllByAclass(AClass aclass);
    List<Student> findAllByCourse(Course course);
    List<Student> findAllByMajor(Major major);
    List<Student> findAllByTerms(Term term);
    boolean existsByEmail(String email);
}
