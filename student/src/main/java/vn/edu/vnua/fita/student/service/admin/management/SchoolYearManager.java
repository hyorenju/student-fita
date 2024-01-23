package vn.edu.vnua.fita.student.service.admin.management;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.SchoolYear;
import vn.edu.vnua.fita.student.entity.Term;
import vn.edu.vnua.fita.student.repository.customrepo.CustomTermRepository;
import vn.edu.vnua.fita.student.repository.jparepo.SchoolYearRepository;
import vn.edu.vnua.fita.student.repository.jparepo.TermRepository;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.ISchoolYearService;
import vn.edu.vnua.fita.student.service.admin.iservice.ITermService;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolYearManager implements ISchoolYearService {
    private final SchoolYearRepository schoolYearRepository;

    @Override
    public List<SchoolYear> getAllSchoolYear() {
        return schoolYearRepository.findAll(Sort.by("id").descending());
    }

    @Override
    @Scheduled(cron = "0 0 1 1 5 ?")
//    @Scheduled(cron = "15 * * * * ?")
    public void createSchoolYearPeriodic() {
        String yearId = schoolYearRepository.findFirstByOrderByIdDesc().getId();
        String[] years = yearId.split("-");
        int year1 = MyUtils.parseIntegerFromString(years[0]) + 1;
        int year2 = MyUtils.parseIntegerFromString(years[1]) + 1;
        String newSchoolYearId = year1 + "-" + year2;
        SchoolYear newSchoolYear = SchoolYear.builder().id(newSchoolYearId).build();
        schoolYearRepository.saveAndFlush(newSchoolYear);
    }
}
