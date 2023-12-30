package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String name;

    @Column(length = 190, unique = true)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(length = 1000)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            //vai tro
            authorities.add(new SimpleGrantedAuthority(role.getId()));
            //quyen
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }

    @OneToMany(mappedBy = "deletedBy", cascade = CascadeType.ALL)
    private Collection<TrashAdmin> trashAdmins;

    @OneToMany(mappedBy = "deletedBy", cascade = CascadeType.ALL)
    private Collection<TrashNews> trashNews;

    @OneToMany(mappedBy = "deletedBy", cascade = CascadeType.ALL)
    private Collection<TrashPoint> trashPoints;

    @OneToMany(mappedBy = "deletedBy", cascade = CascadeType.ALL)
    private Collection<TrashPointOfYear> trashPointOfYears;

    @OneToMany(mappedBy = "deletedBy", cascade = CascadeType.ALL)
    private Collection<TrashStudent> trashStudents;
}
