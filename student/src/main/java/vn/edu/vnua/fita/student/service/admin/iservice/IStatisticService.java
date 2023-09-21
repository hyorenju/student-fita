package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.entity.ClassClassification;
import vn.edu.vnua.fita.student.entity.CourseClassification;
import vn.edu.vnua.fita.student.entity.DropoutStatistic;
import vn.edu.vnua.fita.student.entity.MajorClassification;

public interface IStatisticService {
    void createStatisticPeriodic();
    void createClassClassification();
    void createCourseClassification();
    void createMajorClassification();
    DropoutStatistic getDropoutStatistic(String fromTerm, String toTerm);
    ClassClassification getClassClassification(String classId, String fromTerm, String toTerm);
    CourseClassification getCourseClassification(String courseId, String fromTerm, String toTerm);
    MajorClassification getMajorClassification(String majorId, String fromTerm, String toTerm);
}
