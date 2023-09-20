package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.repository.customrepo.CustomClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.request.admin.aclass.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.GetClassListRequest;
import vn.edu.vnua.fita.student.request.admin.aclass.UpdateClassRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IClassService;

@Service
@RequiredArgsConstructor
public class ClassManager implements IClassService {
    private final ClassRepository classRepository;
    private final String classHadExisted = "Mã lớp đã tồn tại trong hệ thống";
    private final String classNotFound = "Mã lớp %s không tồn tại trong hệ thống";



    @Override
    public Page<AClass> getClassList(GetClassListRequest request) {
        Specification<AClass> specification = CustomClassRepository.filterClassList(
                request.getId()
        );
        return classRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public AClass createClass(CreateClassRequest request) {
        if(classRepository.existsById(request.getId())){
            throw new RuntimeException(classHadExisted);
        }
        AClass aClass = AClass.builder()
                .id(request.getId())
                .name(request.getName())
                .build();

        return classRepository.saveAndFlush(aClass);
    }

    @Override
    public AClass updateClass(UpdateClassRequest request) {
        AClass aClass = classRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(String.format(classNotFound, request.getId())));
        aClass.setName(request.getName());
        return classRepository.saveAndFlush(aClass);
    }

    @Override
    public AClass deleteClass(String id) {
        AClass aClass = classRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(classNotFound, id)));
        classRepository.deleteById(id);
        return aClass;
    }
}
