package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Course;
import vn.edu.vnua.fita.student.repository.customrepo.CustomCourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.fita.student.request.admin.course.GetCourseListRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.ICourseService;

@Service
@RequiredArgsConstructor
public class CourseManager implements ICourseService {
    private final CourseRepository courseRepository;
    private final String courseHadExisted = "Mã khoá đã tồn tại trong hệ thống";
    private final String courseNotFound = "Mã khoá %s không tồn tại trong hệ thống";
    private final String cannotDelete = "Khoá này đang ràng buộc với bảng sinh viên, vui lòng xoá hết sinh viên thuộc khoá này trước khi tiến hành xoá khoá";


    @Override
    public Page<Course> getCourseList(GetCourseListRequest request) {
        Specification<Course> specification = CustomCourseRepository.filterCourseList(
                request.getId()
        );
        return courseRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Course createCourse(CreateCourseRequest request) {
        if (courseRepository.existsById(request.getId())) {
            throw new RuntimeException(courseHadExisted);
        }
        return courseRepository.saveAndFlush(buildCourse(request.getId()));
    }

    @Override
    public Course deleteCourse(String id) {
        try {
            Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(courseNotFound, id)));
            courseRepository.deleteById(id);
            return course;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(cannotDelete);
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1 ?")
//    @Scheduled(cron = "15 * * * * ?")
    public void createCoursePeriodic() {
        String courseId = courseRepository.findFirstByOrderByIdDesc().getId();
        String newCourseId = "" + (Integer.parseInt(courseId) + 1);
        courseRepository.saveAndFlush(buildCourse(newCourseId));
    }

    private Course buildCourse(String courseId) {
        return Course.builder()
                .id(courseId)
                .name("Khóa " + courseId)
                .build();
    }
}
