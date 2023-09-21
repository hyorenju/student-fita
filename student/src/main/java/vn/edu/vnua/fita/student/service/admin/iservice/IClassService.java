package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.model.entity.AClass;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;

public interface IClassService {
    Page<AClass> getClassList(GetClassListRequest request);
    AClass createClass(CreateClassRequest request);
    AClass updateClass(UpdateClassRequest request);
    AClass deleteClass(String id);
}
