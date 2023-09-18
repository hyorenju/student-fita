package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.entity.TrashAdmin;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.request.admin.admin.*;

import java.io.IOException;

public interface IAdminService {
    Page<Admin> getAdminList(GetAdminListRequest request);
    Admin createAdmin(CreateAdminRequest request);
    Admin updateAdmin(UpdateAdminRequest request);
    Admin updateProfile(UpdateProfileRequest request);
    TrashAdmin deleteAdmin(String id);
    TrashAdmin restoreAdmin(Long id);
    Page<TrashAdmin> getTrashAdminList(GetTrashAdminRequest request);
    Admin updateAvatar(MultipartFile file, String id) throws IOException;
}
