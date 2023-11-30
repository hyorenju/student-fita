package vn.edu.vnua.fita.student.request.admin.pointofyear;

import lombok.Data;

import java.util.List;

@Data
public class DeletePointYearRequest {
    private List<Long> ids;
}
