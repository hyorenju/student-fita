package vn.edu.vnua.fita.student.service.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.dto.StudentDTO;
import vn.edu.vnua.fita.student.entity.*;
import vn.edu.vnua.fita.student.model.statistic.*;
import vn.edu.vnua.fita.student.model.statistic.chartform.CircleChart;
import vn.edu.vnua.fita.student.model.statistic.chartform.GroupedColumnChart;
import vn.edu.vnua.fita.student.repository.customrepo.*;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.request.admin.statistic.GetStatisticRequest;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final PointRepository pointRepository;
    private final PointYearRepository pointYearRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final ClassRepository classRepository;
    private final ClassClassificationRepository classClassificationRepository;
    private final CourseRepository courseRepository;
    private final CourseClassificationRepository courseClassificationRepository;
    private final MajorRepository majorRepository;
    private final MajorClassificationRepository majorClassificationRepository;
    private final FacultyClassificationRepository facultyClassificationRepository;
    private final ModelMapper modelMapper;

    @Override
    @Scheduled(cron = "0 0 0 4 4,11 ?")
    public void createClassClassificationPeriodic() {
        List<ClassClassification> classClassifications = new ArrayList<>();
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
//        Term term = termRepository.findFirstByOrderByIdDesc();
            if (classClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<AClass> classes = classRepository.findAll();
                for (AClass aclass :
                        classes) {
                    List<Student> students = studentRepository.findAllByAclass(aclass);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    if (classificationCounter != null) {
                        ClassClassification classClassification = ClassClassification.builder()
                                .aclass(aclass)
                                .term(term)
                                .excellent(classificationCounter.getExcellent())
                                .good(classificationCounter.getGood())
                                .fair(classificationCounter.getFair())
                                .medium(classificationCounter.getMedium())
                                .weak(classificationCounter.getWeak())
                                .worst(classificationCounter.getWorst())
                                .build();
                        classClassifications.add(classClassification);
                    }
                }
            }
        }
        classClassificationRepository.saveAllAndFlush(classClassifications);
    }

    @Override
    @Scheduled(cron = "0 0 0 3 4,11 ?")
    public void createCourseClassificationPeriodic() {
        List<CourseClassification> courseClassifications = new ArrayList<>();
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
//        Term term = termRepository.findFirstByOrderByIdDesc();
            if (courseClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Course> courses = courseRepository.findAll();
                for (Course course :
                        courses) {
                    List<Student> students = studentRepository.findAllByCourse(course);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    if (classificationCounter != null) {
                        CourseClassification courseClassification = CourseClassification.builder()
                                .course(course)
                                .term(term)
                                .excellent(classificationCounter.getExcellent())
                                .good(classificationCounter.getGood())
                                .fair(classificationCounter.getFair())
                                .medium(classificationCounter.getMedium())
                                .weak(classificationCounter.getWeak())
                                .worst(classificationCounter.getWorst())
                                .build();
                        courseClassifications.add(courseClassification);
                    }
                }
            }
        }
        courseClassificationRepository.saveAllAndFlush(courseClassifications);
    }

    @Override
    @Scheduled(cron = "0 0 0 2 4,11 ?")
    public void createMajorClassificationPeriodic() {
        List<MajorClassification> majorClassifications = new ArrayList<>();
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
//        Term term = termRepository.findFirstByOrderByIdDesc();
            if (majorClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Major> majors = majorRepository.findAll();
                for (Major major :
                        majors) {
                    List<Student> students = studentRepository.findAllByMajor(major);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    if (classificationCounter != null) {
                        MajorClassification majorClassification = MajorClassification.builder()
                                .major(major)
                                .term(term)
                                .excellent(classificationCounter.getExcellent())
                                .good(classificationCounter.getGood())
                                .fair(classificationCounter.getFair())
                                .medium(classificationCounter.getMedium())
                                .weak(classificationCounter.getWeak())
                                .worst(classificationCounter.getWorst())
                                .build();
                        majorClassifications.add(majorClassification);
                    }
                }
            }
        }
        majorClassificationRepository.saveAllAndFlush(majorClassifications);
    }

    @Override
    @Scheduled(cron = "0 0 12 1 4,11 ?")
    public void createFacultyClassificationPeriodic() {
        List<FacultyClassification> facultyClassifications = new ArrayList<>();
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
//        Term term = termRepository.findFirstByOrderByIdDesc();
            FacultyClassification facultyClassification = new FacultyClassification();
            if (facultyClassificationRepository.findByTerm(term) == null) {
                String termId = term.getId();
                Integer forcedOut = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 4).size();
                Integer dropoutWithPermission = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 3).size();

                facultyClassification.setTerm(term);
                facultyClassification.setForcedOut(forcedOut);
                facultyClassification.setDropoutWithPermission(dropoutWithPermission);
            }
            if (facultyClassificationRepository.findByTerm(term) == null) {
                String termId = term.getId();
                List<Student> students = studentRepository.findAllByTerms(term);
                ClassificationCounter classificationCounter = countClassified(students, termId);

                facultyClassification.setExcellent(classificationCounter.getExcellent());
                facultyClassification.setGood(classificationCounter.getGood());
                facultyClassification.setFair(classificationCounter.getFair());
                facultyClassification.setMedium(classificationCounter.getMedium());
                facultyClassification.setWeak(classificationCounter.getWeak());
                facultyClassification.setWorst(classificationCounter.getWorst());
            }
            facultyClassifications.add(facultyClassification);
        }

        List<SchoolYear> schoolYears = schoolYearRepository.findAll();
        for (SchoolYear schoolYear :
                schoolYears) {
//            SchoolYear year = schoolYearRepository.findFirstByOrderByIdDesc();
            FacultyClassification facultyClassification = new FacultyClassification();
            if (facultyClassificationRepository.findByYear(schoolYear) == null) {
                String[] years = schoolYear.getId().split("-");
                String termId1 = years[0] + "1";
                String termId2 = years[0] + "2";
                Integer admission =
                        studentStatusRepository.findAllByTermIdAndStatusId(termId1, 1).size() +
                                studentStatusRepository.findAllByTermIdAndStatusId(termId2, 1).size();
                Integer graduate =
                        studentStatusRepository.findAllByTermIdAndStatusId(termId1, 5).size() +
                                studentStatusRepository.findAllByTermIdAndStatusId(termId2, 5).size();

                facultyClassification.setYear(schoolYear);
                facultyClassification.setAdmission(admission);
                facultyClassification.setGraduate(graduate);
            }

            facultyClassifications.add(facultyClassification);
        }

        facultyClassificationRepository.saveAllAndFlush(facultyClassifications);
    }

    @Override
    public StudentStatistic getStudentStatistic(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        List<Point> points = pointRepository.findAllByStudentIdOrderByTermId(id);
        List<StudentStatistic.AvgPoint4> avgPoint4List = new ArrayList<>();
        List<StudentStatistic.AvgPoint10> avgPoint10List = new ArrayList<>();
        List<StudentStatistic.TrainingPoint> trainingPointList = new ArrayList<>();
        for (Point point :
                points) {
            avgPoint4List.add(StudentStatistic.AvgPoint4.builder()
                    .termId(point.getTerm().getId())
                    .point(point.getAvgPoint4())
                    .build());
            avgPoint10List.add(StudentStatistic.AvgPoint10.builder()
                    .termId(point.getTerm().getId())
                    .point(point.getAvgPoint10())
                    .build());
            trainingPointList.add(StudentStatistic.TrainingPoint.builder()
                    .termId(point.getTerm().getId())
                    .point(point.getTrainingPoint())
                    .build());
        }
        Point lastPoint;
        Integer creditsAcc = 0;
        Float accPoint4 = (float) 0;
        if (!points.isEmpty()) {
            lastPoint = points.get(points.size() - 1);
            creditsAcc = lastPoint.getCreditsAcc();
            accPoint4 = lastPoint.getPointAcc4();
        }
        Integer totalCredits = student.getMajor().getTotalCredits();
        return StudentStatistic.builder()
                .student(studentDTO)
                .avgPoint4List(avgPoint4List)
                .avgPoint10List(avgPoint10List)
                .trainingPointList(trainingPointList)
                .creditsAcc(creditsAcc)
                .totalCredits(totalCredits)
                .accPoint4(accPoint4)
                .build();
    }

    @Override
    public List<ClassChart> getClassClassification(String classId, GetStatisticRequest request) {
        Specification<ClassClassification> specification = CustomClassClassificationRepository.filterClassClassificationList(
                classId,
                request.getStart(),
                request.getEnd()
        );
        List<ClassClassification> classClassifications = classClassificationRepository.findAll(specification);

        List<ClassChart> classCharts = new ArrayList<>();
        for (ClassClassification classClassification :
                classClassifications) {
            ClassChart classChart = new ClassChart();
            classChart.setAclass(classClassification.getAclass());
            classChart.setTerm(classClassification.getTerm());
            classChart.setChart(createCircleChart(
                    classClassification.getExcellent(),
                    classClassification.getGood(),
                    classClassification.getFair(),
                    classClassification.getMedium(),
                    classClassification.getWeak(),
                    classClassification.getWorst()
            ));

            classCharts.add(classChart);
        }

        return classCharts;
    }

    @Override
    public List<CourseChart> getCourseClassification(String courseId, GetStatisticRequest request) {
        Specification<CourseClassification> specification = CustomCourseClassificationRepository.filterCourseClassificationList(
                courseId,
                request.getStart(),
                request.getEnd()
        );
        List<CourseClassification> courseClassifications = courseClassificationRepository.findAll(specification);

        List<CourseChart> courseCharts = new ArrayList<>();
        for (CourseClassification courseClassification :
                courseClassifications) {
            CourseChart courseChart = new CourseChart();
            courseChart.setCourse(courseClassification.getCourse());
            courseChart.setTerm(courseClassification.getTerm());
            courseChart.setChart(createCircleChart(
                    courseClassification.getExcellent(),
                    courseClassification.getGood(),
                    courseClassification.getFair(),
                    courseClassification.getMedium(),
                    courseClassification.getWeak(),
                    courseClassification.getWorst()
            ));

            courseCharts.add(courseChart);
        }

        return courseCharts;
    }

    @Override
    public List<MajorChart> getMajorClassification(String majorId, GetStatisticRequest request) {
        Specification<MajorClassification> specification = CustomMajorClassificationRepository.filterMajorClassificationList(
                majorId,
                request.getStart(),
                request.getEnd()
        );
        List<MajorClassification> majorClassifications = majorClassificationRepository.findAll(specification);

        List<MajorChart> majorCharts = new ArrayList<>();
        for (MajorClassification majorClassification :
                majorClassifications) {
            MajorChart majorChart = new MajorChart();
            majorChart.setMajor(majorClassification.getMajor());
            majorChart.setTerm(majorClassification.getTerm());
            majorChart.setChart(createCircleChart(
                    majorClassification.getExcellent(),
                    majorClassification.getGood(),
                    majorClassification.getFair(),
                    majorClassification.getMedium(),
                    majorClassification.getWeak(),
                    majorClassification.getWorst()
            ));

            majorCharts.add(majorChart);
        }

        return majorCharts;
    }

    @Override
    public FacultyColumnChart getFacultyInterruptColumnChart(GetStatisticRequest request) {
        Specification<FacultyClassification> specification = CustomFacultyClassificationRepository.filterFacultyInterruptClassificationList(
                request.getStart(),
                request.getEnd()
        );
        List<FacultyClassification> facultyClassifications = facultyClassificationRepository.findAll(specification);
        return createInterruptColumnChart(facultyClassifications);
    }

    @Override
    public FacultyColumnChart getFacultyIOColumnChart(GetStatisticRequest request) {
        Specification<FacultyClassification> specification = CustomFacultyClassificationRepository.filterFacultyIOClassificationList(
                request.getStart(),
                request.getEnd()
        );
        List<FacultyClassification> facultyClassifications = facultyClassificationRepository.findAll(specification);
        return createIOColumnChart(facultyClassifications);
    }

    @Override
    public List<FacultyCircleChart> getFacultyCircleChart(GetStatisticRequest request) {
        Specification<FacultyClassification> specification = CustomFacultyClassificationRepository.filterFacultyInterruptClassificationList(
                request.getStart(),
                request.getEnd()
        );
        List<FacultyClassification> facultyClassifications = facultyClassificationRepository.findAll(specification);

        List<FacultyCircleChart> facultyCircleCharts = new ArrayList<>();
        for (FacultyClassification facultyClassification :
                facultyClassifications) {
            FacultyCircleChart facultyCircleChart = new FacultyCircleChart();
            facultyCircleChart.setTerm(facultyClassification.getTerm());
            facultyCircleChart.setChart(createCircleChart(
                    facultyClassification.getExcellent(),
                    facultyClassification.getGood(),
                    facultyClassification.getFair(),
                    facultyClassification.getMedium(),
                    facultyClassification.getWeak(),
                    facultyClassification.getWorst()
            ));

            facultyCircleCharts.add(facultyCircleChart);
        }

        return facultyCircleCharts;
    }

    private ClassificationCounter countClassified(List<Student> students, String termId) {
        int excellent = 0;
        int good = 0;
        int fair = 0;
        int medium = 0;
        int weak = 0;
        int worse = 0;

        for (Student student :
                students) {
            Optional<Point> pointOptional = pointRepository.findByStudentIdAndTermIdAndIsDeleted(student.getId(), termId, false);
            if (pointOptional.isPresent()) {
                Integer trainingPoint = pointOptional.get().getTrainingPoint();
                if (trainingPoint != null && trainingPoint >= 90) {
                    excellent++;
                } else if (trainingPoint != null && trainingPoint >= 80) {
                    good++;
                } else if (trainingPoint != null && trainingPoint >= 65) {
                    fair++;
                } else if (trainingPoint != null && trainingPoint >= 50) {
                    medium++;
                } else if (trainingPoint != null && trainingPoint >= 35) {
                    weak++;
                } else if (trainingPoint != null && trainingPoint >= 0) {
                    worse++;
                }
            }
        }

        if (excellent == 0 && good == 0 && fair == 0 && medium == 0 && weak == 0 && worse == 0) {
            return null;
        } else {
            return ClassificationCounter.builder()
                    .excellent(excellent)
                    .good(good)
                    .fair(fair)
                    .medium(medium)
                    .weak(weak)
                    .worst(worse)
                    .build();
        }
    }

    private List<CircleChart> createCircleChart(Integer excellent,
                                                Integer good,
                                                Integer fair,
                                                Integer medium,
                                                Integer weak,
                                                Integer worst) {
        List<CircleChart> charts = new ArrayList<>();
        CircleChart chart1 = CircleChart.builder()
                .type("Xuất sắc")
                .value(excellent)
                .build();
        CircleChart chart2 = CircleChart.builder()
                .type("Tốt")
                .value(good)
                .build();
        CircleChart chart3 = CircleChart.builder()
                .type("Khá")
                .value(fair)
                .build();
        CircleChart chart4 = CircleChart.builder()
                .type("Trung bình")
                .value(medium)
                .build();
        CircleChart chart5 = CircleChart.builder()
                .type("Yếu")
                .value(weak)
                .build();
        CircleChart chart6 = CircleChart.builder()
                .type("Kém")
                .value(worst)
                .build();
        charts.add(chart1);
        charts.add(chart2);
        charts.add(chart3);
        charts.add(chart4);
        charts.add(chart5);
        charts.add(chart6);
        return charts;
    }

    private FacultyColumnChart createInterruptColumnChart(List<FacultyClassification> facultyClassifications) {
        List<GroupedColumnChart> charts = new ArrayList<>();
        for (FacultyClassification facultyClassification :
                facultyClassifications) {
            GroupedColumnChart chart = GroupedColumnChart.builder()
                    .name("Bị buộc thôi học")
                    .time(facultyClassification.getTerm().getId())
                    .quantity(facultyClassification.getForcedOut())
                    .build();
            charts.add(chart);
        }
        for (FacultyClassification facultyClassification :
                facultyClassifications) {
            GroupedColumnChart chart = GroupedColumnChart.builder()
                    .name("Đã xin thôi học")
                    .time(facultyClassification.getTerm().getId())
                    .quantity(facultyClassification.getDropoutWithPermission())
                    .build();
            charts.add(chart);
        }
        return FacultyColumnChart.builder()
                .chart(charts)
                .build();
    }

    private FacultyColumnChart createIOColumnChart(List<FacultyClassification> facultyClassifications) {
        List<GroupedColumnChart> charts = new ArrayList<>();
        for (FacultyClassification facultyClassification :
                facultyClassifications) {
            GroupedColumnChart chart = GroupedColumnChart.builder()
                    .name("Đã nhập học")
                    .time(facultyClassification.getYear().getId())
                    .quantity(facultyClassification.getForcedOut())
                    .build();
            charts.add(chart);
        }
        for (FacultyClassification facultyClassification :
                facultyClassifications) {
            GroupedColumnChart chart = GroupedColumnChart.builder()
                    .name("Đã tốt nghiệp")
                    .time(facultyClassification.getYear().getId())
                    .quantity(facultyClassification.getDropoutWithPermission())
                    .build();
            charts.add(chart);
        }
        return FacultyColumnChart.builder()
                .chart(charts)
                .build();
    }
}