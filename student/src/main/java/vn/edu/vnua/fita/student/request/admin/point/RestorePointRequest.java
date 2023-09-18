package vn.edu.vnua.fita.student.request.admin.point;

import lombok.Data;

import java.util.List;

@Data
public class RestorePointRequest {
    private List<Long> ids;
}
