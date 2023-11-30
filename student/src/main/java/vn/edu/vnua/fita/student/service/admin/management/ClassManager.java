package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.dto.MajorDTO;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Role;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.repository.customrepo.CustomClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IClassService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassManager implements IClassService {
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final String classHadExisted = "Mã lớp đã tồn tại trong hệ thống";
    private final String classNotFound = "Mã lớp %s không tồn tại trong hệ thống";
    private final String cannotDelete = "Lớp này đang ràng buộc với bảng sinh viên, vui lòng xoá hết sinh viên thuộc lớp này trước khi tiến hành xoá lớp";
    private final String studentNotFound = "Không tìm thấy sinh viên %s";
    private final String duplicateMonitor = "Có ít nhất 1 sinh viên đã là ban cán sự ở nơi khác, vui lòng nhập lại";

    @Override
    public Page<AClass> getClassList(GetClassListRequest request) {
        Specification<AClass> specification = CustomClassRepository.filterClassList(
                request.getId()
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

            String monitorId = request.getClassOfficers().getMonitor();
            String viceMonitorId = request.getClassOfficers().getViceMonitor();
            String secretaryId = request.getClassOfficers().getSecretary();
            String deputySecretaryId = request.getClassOfficers().getDeputySecretary();

            if(monitorId!=null) {
                monitor = studentRepository.findById(monitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, monitorId)));
                monitor.setRole(Role.builder().id(RoleConstant.MONITOR).build());
//                studentRepository.saveAndFlush(monitor);
            }
            if(viceMonitorId != null) {
                viceMonitor = studentRepository.findById(viceMonitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, viceMonitorId)));
            }
            if(secretaryId != null) {
                secretary = studentRepository.findById(secretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, secretaryId)));
            }
            if(deputySecretaryId != null) {
                deputySecretary = studentRepository.findById(deputySecretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, deputySecretaryId)));
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
            throw new RuntimeException(duplicateMonitor);
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

            String monitorId = request.getClassOfficers().getMonitor();
            String viceMonitorId = request.getClassOfficers().getViceMonitor();
            String secretaryId = request.getClassOfficers().getSecretary();
            String deputySecretaryId = request.getClassOfficers().getDeputySecretary();

            if(monitorId!=null) {
                newMonitor = studentRepository.findById(monitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, monitorId)));
                newMonitor.setRole(Role.builder().id(RoleConstant.MONITOR).build());
//                studentRepository.saveAndFlush(newMonitor);

                Student oldMonitor = aClass.getMonitor();
                if(oldMonitor != null) {
                    oldMonitor.setRole(Role.builder().id(RoleConstant.STUDENT).build());
//                    studentRepository.saveAndFlush(oldMonitor);
                }
            } else {
                Student oldMonitor = aClass.getMonitor();
                if (oldMonitor != null) {
                    oldMonitor.setRole(Role.builder().id(RoleConstant.STUDENT).build());
                }
            }
            if(viceMonitorId != null) {
                newViceMonitor = studentRepository.findById(viceMonitorId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, viceMonitorId)));
            }
            if(secretaryId != null) {
                newSecretary = studentRepository.findById(secretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, secretaryId)));
            }
            if(deputySecretaryId != null) {
                newDeputySecretary = studentRepository.findById(deputySecretaryId).orElseThrow(() -> new RuntimeException(String.format(studentNotFound, deputySecretaryId)));
            }

            aClass.setName(request.getName());
            aClass.setMonitor(newMonitor);
            aClass.setViceMonitor(newViceMonitor);
            aClass.setSecretary(newSecretary);
            aClass.setDeputySecretary(newDeputySecretary);
            return classRepository.saveAndFlush(aClass);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(duplicateMonitor);
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
}
