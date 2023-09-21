package vn.edu.vnua.fita.student.service.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.ClassClassification;
import vn.edu.vnua.fita.student.entity.CourseClassification;
import vn.edu.vnua.fita.student.entity.DropoutStatistic;
import vn.edu.vnua.fita.student.entity.MajorClassification;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatisticService;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final TermRepository termRepository;
    private final PointRepository pointRepository;
    private final StudentStatusRepository studentStatusRepository;

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createStatisticPeriodic() {

    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createClassClassification() {

    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createCourseClassification() {

    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createMajorClassification() {

    }

    @Override
    public DropoutStatistic getDropoutStatistic(String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public ClassClassification getClassClassification(String classId, String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public CourseClassification getCourseClassification(String courseId, String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public MajorClassification getMajorClassification(String majorId, String fromTerm, String toTerm) {
        return null;
    }
}
