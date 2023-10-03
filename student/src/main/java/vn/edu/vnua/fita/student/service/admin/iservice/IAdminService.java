package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.model.entity.TrashAdmin;
import vn.edu.vnua.fita.student.model.entity.Admin;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
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
    Admin updateAvatar(MultipartFile file) throws IOException;
    Admin changePassword(ChangePasswordRequest request);
}
