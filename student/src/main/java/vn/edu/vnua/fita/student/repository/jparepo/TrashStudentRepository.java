package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.TrashStudent;
import vn.edu.vnua.fita.student.model.entity.Student;

@Repository
public interface TrashStudentRepository extends JpaRepository<TrashStudent, Long> {
    TrashStudent findByStudent(Student student);
}
