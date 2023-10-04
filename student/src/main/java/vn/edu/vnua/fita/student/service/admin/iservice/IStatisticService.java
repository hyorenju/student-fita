package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.entity.*;
import vn.edu.vnua.fita.student.model.statistic.*;
import vn.edu.vnua.fita.student.request.admin.statistic.GetStatisticRequest;

import java.util.List;

public interface IStatisticService {
    void createClassClassificationPeriodic();
    void createCourseClassificationPeriodic();
    void createMajorClassificationPeriodic();
    void createFacultyClassificationPeriodic();
    StudentStatistic getStudentStatistic(String id);
    List<ClassChart> getClassClassification(String classId, GetStatisticRequest request);
    List<CourseChart> getCourseClassification(String courseId, GetStatisticRequest request);
    List<MajorChart> getMajorClassification(String majorId, GetStatisticRequest request);
    List<FacultyClassification> getFacultyColumnChart(GetStatisticRequest request);
    List<FacultyChart> getFacultyCircleChart(GetStatisticRequest request);
}
