package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.statistic.*;
import vn.edu.vnua.fita.student.request.admin.statistic.GetCircleStatisticRequest;
import vn.edu.vnua.fita.student.request.admin.statistic.GetStatisticRequest;

import java.util.List;

public interface IStatisticService {
    void createClassClassificationPeriodic();
    void createCourseClassificationPeriodic();
    void createMajorClassificationPeriodic();
    void createFacultyClassificationPeriodic();
    StudentStatistic getStudentStatistic(String id);
    List<ClassChart> getClassClassification(String classId, GetCircleStatisticRequest request);
    List<CourseChart> getCourseClassification(String courseId, GetCircleStatisticRequest request);
    List<MajorChart> getMajorClassification(String majorId, GetCircleStatisticRequest request);
    FacultyColumnChart getFacultyInterruptColumnChart(GetStatisticRequest request);
    FacultyColumnChart getFacultyIOColumnChart(GetStatisticRequest request);
    List<FacultyCircleChart> getFacultyCircleChart(GetCircleStatisticRequest request);
}
