package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.vnua.fita.student.model.entity.RefreshToken;
import vn.edu.vnua.fita.student.model.entity.Student;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByStudent(Student student);
    RefreshToken findByStudent(Student student);
    RefreshToken findByToken(String token);
}
