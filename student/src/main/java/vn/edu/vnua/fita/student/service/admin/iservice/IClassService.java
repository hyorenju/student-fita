package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;

import java.util.List;

public interface IClassService {
    Page<AClass> getClassList(GetClassListRequest request);
    List<AClass> getAllClass();
    AClass createClass(CreateClassRequest request);
    AClass updateClass(UpdateClassRequest request);
    AClass deleteClass(String id);
}
