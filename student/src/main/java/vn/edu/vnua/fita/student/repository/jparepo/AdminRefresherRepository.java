package vn.edu.vnua.fita.student.repository.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.AdminRefresher;

import java.util.List;

public interface AdminRefresherRepository extends JpaRepository<AdminRefresher, Long> {
    boolean existsByAdmin(Admin admin);
    AdminRefresher findByAdmin(Admin admin);
    AdminRefresher findByToken(String token);
    List<AdminRefresher> findAllByAdmin(Admin admin);
    void deleteByAdmin(Admin admin);
}
