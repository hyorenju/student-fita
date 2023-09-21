package vn.edu.vnua.fita.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AdminLoginResponse extends BaseLoginResponse{
    private String id;

    private String name;

    private String avatar;

    private String email;

    public AdminLoginResponse(String jwt, String roleId, String id, String name, String avatar, String email) {
        super(jwt, roleId);
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
    }
}
