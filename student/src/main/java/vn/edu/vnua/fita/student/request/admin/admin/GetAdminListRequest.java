package vn.edu.vnua.fita.student.request.admin.admin;

import lombok.Data;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;


@Data
public class GetAdminListRequest extends GetPageBaseRequest {
    private String id;
}
