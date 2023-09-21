package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.model.entity.*;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;

import java.util.List;

public interface IStatisticService {
    void createDropoutStatisticPeriodic();
    void createClassClassification();
    void createCourseClassification();
    void createMajorClassification();
    void createFacultyClassification();
    StudentStatistic getStudentStatistic(String id);
    List<DropoutStatistic> getDropoutStatistic(String fromTerm, String toTerm);
    List<ClassClassification> getClassClassification(String classId, String fromTerm, String toTerm);
    List<CourseClassification> getCourseClassification(String courseId, String fromTerm, String toTerm);
    List<MajorClassification> getMajorClassification(String majorId, String fromTerm, String toTerm);
    List<FacultyClassification> getFacultyClassification();
}
