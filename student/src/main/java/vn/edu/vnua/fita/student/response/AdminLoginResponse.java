package vn.edu.vnua.fita.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AdminLoginResponse extends BaseLoginResponse{
    private String refreshToken;
//    private String id;
//    private String name;
    private String avatar;
//    private String email;

    public AdminLoginResponse(String jwt, String roleId, String refreshToken, String avatar) {
        super(jwt, roleId);
        this.refreshToken = refreshToken;
        this.avatar = avatar;
    }
}
