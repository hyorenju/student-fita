package vn.edu.vnua.fita.student.service.admin.management;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.repository.customrepo.CustomAdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRefresherRepository;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.RoleRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TrashAdminRepository;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.admin.admin.*;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.admin.iservice.IAdminService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class    AdminManager implements IAdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final TrashAdminRepository trashAdminRepository;
    private final AdminRefresherRepository adminRefresherRepository;
    private final PasswordEncoder encoder;
    private final FirebaseService firebaseService;
    private final String adminHadExisted = "Mã quản trị viên đã tồn tại trong hệ thống";
    private final String emailHadUsed = "Email đã được sử dụng cho một tài khoản khác";
    private final String roleNotFound = "Mã quyền không tồn tại trong hệ thống";
    private final String validAdminId = "Mã quản trị viên phải bắt đầu bằng chữ cái";
    private final String adminNotFound = "Không tìm thấy quản trị viên";
    private final String byWhomNotFound = "Không thể xác định danh tính người xoá";
    private final String trashNotFound = "Không tìm thấy rác";
    private final String emailHasExisted = "Email %s đã tồn tại trong hệ thống, vui lòng sử dụng một email khác";
    private final String cannotUpdate = "Không thể cập nhật quản trị viên cấp cao";
    private final String cannotDelete = "Không thể xoá quản trị viên cấp cao";

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Admin getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(adminNotFound));
    }

    @Override
    public Page<Admin> getAdminList(GetAdminListRequest request) {
        Specification<Admin> specification = CustomAdminRepository.filterAdminList(
                request.getId()
        );
        return adminRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Admin createAdmin(CreateAdminRequest request) {
        try {
            if (adminRepository.existsById(request.getId())) {
                throw new RuntimeException(adminHadExisted);
            } else if (adminRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException(emailHadUsed);
            } else if (request.getId().matches("^[0-9]+")) {
                throw new RuntimeException(validAdminId);
            }
            Role role = roleRepository.findById(request.getRole().getId()).orElseThrow(() -> new RuntimeException(roleNotFound));
            Admin admin = Admin.builder()
                    .id(request.getId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .role(role)
                    .isDeleted(false)
                    .build();
            return adminRepository.saveAndFlush(admin);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(String.format(emailHasExisted, request.getEmail()));
        }
    }

    @Override
    public Admin updateAdmin(UpdateAdminRequest request) {
        try {
            Role role = roleRepository.findById(request.getRole().getId()).orElseThrow(() -> new RuntimeException(roleNotFound));
            Admin admin = adminRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(adminNotFound));
            if(admin.getRole().getId().equals(RoleConstant.SUPERADMIN)) {
                throw new RuntimeException(cannotUpdate);
            }

            admin.setName(request.getName());
            admin.setEmail(request.getEmail());
            admin.setRole(role);
            if (StringUtils.hasText(request.getPassword())) {
                admin.setPassword(encoder.encode(request.getPassword()));
            }

            return adminRepository.saveAndFlush(admin);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(String.format(emailHasExisted, request.getEmail()));
        }
    }

    @Override
    public Admin updateProfile(UpdateProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(adminNotFound));

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        return adminRepository.saveAndFlush(admin);
    }

    @Override
    public TrashAdmin deleteAdmin(String id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(adminNotFound));
        if (admin.getRole().getId().equals(RoleConstant.SUPERADMIN)) {
            throw new RuntimeException(cannotDelete);
        } else {
            admin.setIsDeleted(true);
            return moveToTrash(admin);
        }

    }

    @Override
    public TrashAdmin deletePermanent(Long id) {
        TrashAdmin trashAdmin = trashAdminRepository.findById(id).orElseThrow(() -> new RuntimeException(trashNotFound));
        Admin admin = trashAdmin.getAdmin();

        List<AdminRefresher> adminRefreshers = adminRefresherRepository.findAllByAdmin(admin);
        for (AdminRefresher adminRefresher:
                adminRefreshers) {
            adminRefresherRepository.delete(adminRefresher);
        }

        trashAdminRepository.delete(trashAdmin);
        adminRepository.delete(admin);

        return trashAdmin;
    }

    @Override
    public TrashAdmin restoreAdmin(Long id) {
        TrashAdmin trashAdmin = trashAdminRepository.findById(id).orElseThrow(() -> new RuntimeException(trashNotFound));
        Admin admin = trashAdmin.getAdmin();
        admin.setIsDeleted(false);
        restoreFromTrash(admin);
        return trashAdmin;
    }

    @Override
    public Page<TrashAdmin> getTrashAdminList(GetTrashAdminRequest request) {
        return trashAdminRepository.findAll(PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public Admin updateAvatar(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(adminNotFound));

        Blob blob = firebaseService.uploadImage(file, bucketName);

        admin.setAvatar(blob
                .signUrl(FirebaseExpirationTimeConstant.EXPIRATION_TIME, TimeUnit.MILLISECONDS)
                .toString());

        return adminRepository.saveAndFlush(admin);
    }

    @Override
    public Admin changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(adminNotFound));

        if (!encoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if (encoder.matches(request.getNewPassword(), admin.getPassword())) {
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
        }

        admin.setPassword(encoder.encode(request.getNewPassword()));
        return adminRepository.saveAndFlush(admin);
    }

    private TrashAdmin moveToTrash(Admin admin) {
        Admin anotherAdmin = findAdminDeletedIt();

        TrashAdmin trashAdmin = TrashAdmin.builder()
                .admin(admin)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .deletedBy(anotherAdmin)
                .build();
        trashAdminRepository.saveAndFlush(trashAdmin);
        return trashAdmin;
    }

    private void restoreFromTrash(Admin admin) {
        TrashAdmin trashAdmin = trashAdminRepository.findByAdmin(admin);
        trashAdminRepository.deleteById(trashAdmin.getId());
    }

    private Admin findAdminDeletedIt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(byWhomNotFound));
    }

    public String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}
