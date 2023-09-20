package vn.edu.vnua.fita.student.service.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Statistic;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatisticService;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final TermRepository termRepository;
    private final PointRepository pointRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final StatisticRepository statisticRepository;

    @Override
    @Scheduled(cron = "0 0 0 1 12 ?")
    public void createStatisticPeriodic() {
        String termId = termRepository.findFirstByOrderByIdDesc().getId();
        Integer dropoutWithoutPermission = studentStatusRepository.findAllByStatusId(2).size();
        Integer dropoutWithPermission = studentStatusRepository.findAllByStatusId(3).size();

    }

    @Override
    public Statistic getStatistic(String fromTerm, String toTerm) {
        return null;
    }
}
