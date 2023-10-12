package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.model.entity.AdminRefresher;

public interface AdminRefresherRepository extends JpaRepository<AdminRefresher, Long> {
    boolean existsByAdmin(Admin admin);
    AdminRefresher findByAdmin(Admin admin);
    AdminRefresher findByToken(String token);
}
