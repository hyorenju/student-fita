package vn.edu.vnua.fita.student.service.admin.iservice;

import vn.edu.vnua.fita.student.entity.Statistic;

public interface IStatisticService {
    void createStatisticPeriodic();
    Statistic getStatistic(String fromTerm, String toTerm);
}
