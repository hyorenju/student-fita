package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.entity.StudentRefresher;
import vn.edu.vnua.fita.student.entity.Student;

@Repository
public interface StudentRefresherRepository extends JpaRepository<StudentRefresher, Long> {
    boolean existsByStudent(Student student);
    StudentRefresher findByStudent(Student student);
    StudentRefresher findByToken(String token);
}
