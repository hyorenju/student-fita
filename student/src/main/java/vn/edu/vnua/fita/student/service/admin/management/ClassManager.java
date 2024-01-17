package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.repository.customrepo.CustomClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.ExportClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.admin.file.ExcelService;
import vn.edu.vnua.fita.student.service.admin.iservice.IClassService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ClassManager implements IClassService {
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final ExcelService excelService;
    private final String classHadExisted = "Mã lớp đã tồn tại trong hệ thống";
    private final String classNotFound = "Mã lớp %s không tồn tại trong hệ thống";
    private final String cannotDelete = "Lớp này đang ràng buộc với bảng sinh viên, vui lòng xoá hết sinh viên thuộc lớp này trước khi tiến hành xoá lớp";
    private final String studentNotFound = "Không tìm thấy sinh viên %s";
    private final String duplicateOfficer = "Có ít nhất 1 sinh viên đã là ban cán sự ở nơi khác, vui lòng nhập lại";
    private final String duplicateMonitor = "Lớp trưởng này đã là ban cán sự ở nơi khác, vui lòng nhập lại";
    private final String duplicateViceMonitor = "Lớp phó này đã là ban cán sự ở nơi khác, vui lòng nhập lại";
    private final String duplicateSecretary = "Bí thư này đã là ban cán sự ở nơi khác, vui lòng nhập lại";
    private final String duplicateDeputySecretary = "Phó bí thư này đã là ban cán sự ở nơi khác, vui lòng nhập lại";


    @Override
    public Page<AClass> getClassList(GetClassListRequest request) {
        Specification<AClass> specification = CustomClassRepository.filterClassList(
                request.getId(),
                request.getMonitorId(),
                request.getViceMonitorId(),
                request.getSecretaryId(),
                request.getDeputySecretaryId()
        );
        return classRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public List<AClass> getAllClass() {
        return classRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public AClass createClass(CreateClassRequest request) {
        try {
            if (classRepository.existsById(request.getId())) {
                throw new RuntimeException(classHadExisted);
            }

            Student monitor = null;
            Student viceMonitor = null;
            Student secretary = null;
            Student deputySecretary = null;

            if (request.getMonitor() != null) {
                String monitorId = request.getMonitor().getId();
                if (StringUtils.hasText(monitorId) &&
                        classRepository.existsByMonitorId(monitorId) &&
                        classRepository.existsByViceMonitorId(monitorId) &&
                        classRepository.existsBySecretaryId(monitorId) &&
                        classRepository.existsByDeputySecretaryId(monitorId)) {
                    monitor = studentRepository.findById(monitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, monitorId)));
                    monitor.setRole(Role.builder().id(RoleConstant.MONITOR).build());
                } else {
                    throw new RuntimeException(duplicateMonitor);
                }
            }
            if (request.getViceMonitor() != null) {
                String viceMonitorId = request.getViceMonitor().getId();
                if (StringUtils.hasText(viceMonitorId) &&
                        classRepository.existsByMonitorId(viceMonitorId) &&
                        classRepository.existsByViceMonitorId(viceMonitorId) &&
                        classRepository.existsBySecretaryId(viceMonitorId) &&
                        classRepository.existsByDeputySecretaryId(viceMonitorId)) {
                    viceMonitor = studentRepository.findById(viceMonitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, viceMonitorId)));
                } else {
                    throw new RuntimeException(duplicateViceMonitor);
                }
            }
            if (request.getSecretary() != null) {
                String secretaryId = request.getSecretary().getId();
                if (StringUtils.hasText(secretaryId) &&
                        classRepository.existsByMonitorId(secretaryId) &&
                        classRepository.existsByViceMonitorId(secretaryId) &&
                        classRepository.existsBySecretaryId(secretaryId) &&
                        classRepository.existsByDeputySecretaryId(secretaryId)) {
                    secretary = studentRepository.findById(secretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, secretaryId)));
                } else {
                    throw new RuntimeException(duplicateSecretary);
                }
            }
            if (request.getDeputySecretary() != null) {
                String deputySecretaryId = request.getDeputySecretary().getId();
                if (StringUtils.hasText(deputySecretaryId) &&
                        classRepository.existsByMonitorId(deputySecretaryId) &&
                        classRepository.existsByViceMonitorId(deputySecretaryId) &&
                        classRepository.existsBySecretaryId(deputySecretaryId) &&
                        classRepository.existsByDeputySecretaryId(deputySecretaryId)) {
                    deputySecretary = studentRepository.findById(deputySecretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, deputySecretaryId)));
                } else {
                    throw new RuntimeException(duplicateDeputySecretary);
                }
            }

            AClass aClass = AClass.builder()
                    .id(request.getId().toUpperCase())
                    .name(request.getName())
                    .monitor(monitor)
                    .secretary(secretary)
                    .viceMonitor(viceMonitor)
                    .deputySecretary(deputySecretary)
                    .build();

            return classRepository.saveAndFlush(aClass);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(duplicateOfficer);
        }
    }

    @Override
    public AClass updateClass(UpdateClassRequest request) {
        try {
            AClass aClass = classRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getId())));

            Student newMonitor = null;
            Student newViceMonitor = null;
            Student newSecretary = null;
            Student newDeputySecretary = null;

            if (request.getMonitor() != null) {
                String monitorId = request.getMonitor().getId();
                if (StringUtils.hasText(monitorId) &&
                        classRepository.existsByMonitorId(monitorId) &&
                        classRepository.existsByViceMonitorId(monitorId) &&
                        classRepository.existsBySecretaryId(monitorId) &&
                        classRepository.existsByDeputySecretaryId(monitorId)) {
                    newMonitor = studentRepository.findById(monitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, monitorId)));
                    newMonitor.setRole(Role.builder().id(RoleConstant.MONITOR).build());

                    Student oldMonitor = aClass.getMonitor();
                    if (oldMonitor != null) {
                        oldMonitor.setRole(Role.builder().id(RoleConstant.STUDENT).build());
                    }
                } else {
                    Student oldMonitor = aClass.getMonitor();
                    if (oldMonitor != null) {
                        oldMonitor.setRole(Role.builder().id(RoleConstant.STUDENT).build());
                    }
                }
            }
            if (request.getViceMonitor() != null) {
                String viceMonitorId = request.getViceMonitor().getId();
                if (StringUtils.hasText(viceMonitorId)) {
                    newViceMonitor = studentRepository.findById(viceMonitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, viceMonitorId)));
                }
            }
            if (request.getSecretary() != null) {
                String secretaryId = request.getSecretary().getId();
                if (StringUtils.hasText(secretaryId)) {
                    newSecretary = studentRepository.findById(secretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, secretaryId)));
                }
            }
            if (request.getDeputySecretary() != null) {
                String deputySecretaryId = request.getDeputySecretary().getId();
                if (StringUtils.hasText(deputySecretaryId)) {
                    newDeputySecretary = studentRepository.findById(deputySecretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, deputySecretaryId)));
                }
            }

            aClass.setName(request.getName());
            aClass.setMonitor(newMonitor);
            aClass.setViceMonitor(newViceMonitor);
            aClass.setSecretary(newSecretary);
            aClass.setDeputySecretary(newDeputySecretary);
            return classRepository.saveAndFlush(aClass);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(duplicateOfficer);
        }
    }

    @Override
    public AClass deleteClass(String id) {
        try {
            AClass aClass = classRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(classNotFound, id)));
            classRepository.delete(aClass);
            return aClass;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(cannotDelete);
        }
    }

    @Override
    public List<AClass> importFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        return classRepository.saveAllAndFlush(excelService.readClassFromExcel(file));
    }

    @Override
    public String exportToExcel(ExportClassListRequest request) {
        Specification<AClass> specification = CustomClassRepository.filterClassList(
                request.getId(),
                request.getMonitorId(),
                request.getViceMonitorId(),
                request.getSecretaryId(),
                request.getDeputySecretaryId()
        );
        List<AClass> classes = classRepository.findAll(specification);

        return excelService.writeClassListToExcel(classes);
    }
}
