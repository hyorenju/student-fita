package vn.edu.vnua.fita.student.service.admin.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.Course;
import vn.edu.vnua.fita.student.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.fita.student.request.admin.course.GetCourseListRequest;

public interface ICourseService {
    Page<Course> getCourseList(GetCourseListRequest request);
    Course createCourse(CreateCourseRequest request);
    Course deleteCourse(String id);

}
