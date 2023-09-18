package vn.edu.vnua.fita.student.service.admin.management;

import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import vn.edu.vnua.fita.student.entity.TrashAdmin;
import vn.edu.vnua.fita.student.entity.Admin;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.repository.customrepo.CustomAdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.AdminRepository;
import vn.edu.vnua.fita.student.repository.jparepo.RoleRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TrashAdminRepository;
import vn.edu.vnua.fita.student.request.admin.admin.*;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.iservice.IAdminService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminManager implements IAdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final TrashAdminRepository trashAdminRepository;
    private final PasswordEncoder encoder;
    private final FirebaseService firebaseService;
    private final String adminHadExisted = "Mã quản trị viên đã tồn tại trong hệ thống";
    private final String emailHadUsed = "Email đã được sử dụng cho một tài khoản khác";
    private final String roleNotFound = "Mã quyền không tồn tại trong hệ thống";
    private final String validAdminId = "Mã quản trị viên phải bắt đầu bằng chữ cái";
    private final String adminNotFound = "Không tìm thấy mã quản trị viên %s";
    private final String byWhomNotFound = "Không thể xác định danh tính người xoá";


    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Page<Admin> getAdminList(GetAdminListRequest request) {
        Specification<Admin> specification = CustomAdminRepository.filterAdminList(
                request.getId()
        );
        return adminRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Admin createAdmin(CreateAdminRequest request) {
        if (adminRepository.existsById(request.getId())) {
            throw new RuntimeException(adminHadExisted);
        }else if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(emailHadUsed);
        } else if (!roleRepository.existsById(request.getRoleId())) {
            throw new RuntimeException(roleNotFound);
        } else if(request.getId().matches("^[0-9]+")){
            throw new RuntimeException(validAdminId);
        }
        Admin admin = Admin.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.builder().id(request.getRoleId()).build())
                .isDeleted(false)
                .build();
        return adminRepository.saveAndFlush(admin);
    }

    @Override
    public Admin updateAdmin(UpdateAdminRequest request) {
        Admin admin = adminRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, request.getId())));
        if (!roleRepository.existsById(request.getRoleId())) {
            throw new RuntimeException(roleNotFound);
        }

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setRole(Role.builder().id(request.getRoleId()).build());
        if (StringUtils.hasText(request.getPassword())) {
            admin.setPassword(encoder.encode(request.getPassword()));
        }

        return admin;
    }

    @Override
    public Admin updateProfile(UpdateProfileRequest request) {
        Admin admin = adminRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, request.getId())));
        admin.setName(request.getInfo().getName());
        admin.setEmail(request.getInfo().getEmail());
        adminRepository.saveAndFlush(admin);
        return admin;
    }

    @Override
    public TrashAdmin deleteAdmin(String id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, id)));
        admin.setIsDeleted(true);
        TrashAdmin trashAdmin = moveToTrash(admin);
        adminRepository.saveAndFlush(admin);
        return trashAdmin;
    }

    @Override
    public TrashAdmin restoreAdmin(Long id) {
        TrashAdmin trashAdmin = trashAdminRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy rác muốn khôi phục"));
        Admin admin = trashAdmin.getAdmin();
        admin.setIsDeleted(false);
        restoreFromTrash(admin);
        adminRepository.saveAndFlush(admin);
        return trashAdmin;
    }

    @Override
    public Page<TrashAdmin> getTrashAdminList(GetTrashAdminRequest request) {
        return trashAdminRepository.findAll(PageRequest.of(request.getPage()-1, request.getSize(), Sort.by("id").descending()));
    }

    @Override
    public Admin updateAvatar(MultipartFile file, String id) throws IOException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(adminNotFound, id)));

        // Tạo một tên file
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + getFileExtension(originalFileName);
        Blob blob = firebaseService.uploadImage(file, fileName, bucketName);

        if(StringUtils.hasText(admin.getAvatar())){
            blob.getStorage().delete(bucketName, fileName);
        }

        admin.setAvatar(blob
                .signUrl(FirebaseExpirationTimeConstant.EXPIRATION_TIME, TimeUnit.MILLISECONDS)
                .toString());

        return adminRepository.saveAndFlush(admin);
    }

    private TrashAdmin moveToTrash(Admin admin) {
        String byWhom = findAdminDeletedIt();

        TrashAdmin trashAdmin = TrashAdmin.builder()
                .admin(admin)
                .time(Timestamp.valueOf(LocalDateTime.now(ZoneId.of(DateTimeConstant.TIME_ZONE))))
                .byWhom(byWhom)
                .build();
        trashAdminRepository.saveAndFlush(trashAdmin);
        return trashAdmin;
    }

    private void restoreFromTrash(Admin admin) {
        TrashAdmin trashAdmin = trashAdminRepository.findByAdmin(admin);
        trashAdminRepository.deleteById(trashAdmin.getId());
    }

    private String findAdminDeletedIt() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(byWhomNotFound));
        return admin.getName();
    }

    public String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}
