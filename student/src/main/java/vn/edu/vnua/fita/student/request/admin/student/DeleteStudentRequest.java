package vn.edu.vnua.fita.student.request.admin.student;

import lombok.Data;

import java.util.List;

@Data
public class DeleteStudentRequest {
    private List<String> ids;
}
