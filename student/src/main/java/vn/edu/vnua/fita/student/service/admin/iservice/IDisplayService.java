package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.model.entity.Display;
import vn.edu.vnua.fita.student.request.admin.display.CreateDisplayRequest;
import vn.edu.vnua.fita.student.request.admin.display.GetDisplayListRequest;
import vn.edu.vnua.fita.student.request.admin.display.UpdateDisplayRequest;

import java.io.IOException;

public interface IDisplayService {
    Page<Display> getDisplayList(GetDisplayListRequest request);
    Display createDisplay(CreateDisplayRequest request);
    Display updateDisplay(Long id, UpdateDisplayRequest request);
    String uploadImg(MultipartFile file) throws IOException;
}
