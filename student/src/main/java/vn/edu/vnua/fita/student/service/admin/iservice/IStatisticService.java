package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.entity.*;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.request.admin.statistic.GetStatisticRequest;

import java.util.List;

public interface IStatisticService {
    void createClassClassificationPeriodic();
    void createCourseClassificationPeriodic();
    void createMajorClassificationPeriodic();
    void createFacultyClassificationPeriodic();
    StudentStatistic getStudentStatistic(String id);
    List<ClassClassification> getClassClassification(String classId, GetStatisticRequest request);
    List<CourseClassification> getCourseClassification(String courseId, GetStatisticRequest request);
    List<MajorClassification> getMajorClassification(String majorId, GetStatisticRequest request);
    List<FacultyClassification> getFacultyClassification(GetStatisticRequest request);
}
