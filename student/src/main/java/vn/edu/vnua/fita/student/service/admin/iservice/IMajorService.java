package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.model.entity.Major;
import vn.edu.vnua.fita.student.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.fita.student.request.admin.major.GetMajorListRequest;
import vn.edu.vnua.fita.student.request.admin.major.UpdateMajorRequest;

public interface IMajorService {
    Page<Major> getMajorList(GetMajorListRequest request);

    Major createMajor(CreateMajorRequest request);

    Major updateMajor(UpdateMajorRequest request);

    Major deleteMajor(String id);
}
