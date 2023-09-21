package vn.edu.vnua.fita.student.service.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.entity.*;
import vn.edu.vnua.fita.student.model.statistic.ClassificationCounter;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatisticService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final PointRepository pointRepository;
    private final StudentStatusRepository studentStatusRepository;
    private final DropoutStatisticRepository dropoutStatisticRepository;
    private final ClassRepository classRepository;
    private final ClassClassificationRepository classClassificationRepository;
    private final CourseRepository courseRepository;
    private final CourseClassificationRepository courseClassificationRepository;
    private final MajorRepository majorRepository;
    private final MajorClassificationRepository majorClassificationRepository;
    private final FacultyClassificationRepository facultyClassificationRepository;

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createDropoutStatisticPeriodic() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            if (dropoutStatisticRepository.findByTerm(term) == null) {
                String termId = term.getId();
                Integer dropoutWithoutPermission = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 2).size();
                Integer dropoutWithPermission = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 3).size();

                DropoutStatistic dropoutStatistic = DropoutStatistic.builder()
                        .term(term)
                        .dropoutWithoutPermission(dropoutWithoutPermission)
                        .dropoutWithPermission(dropoutWithPermission)
                        .build();
                dropoutStatisticRepository.saveAndFlush(dropoutStatistic);
            }
//            Term term = termRepository.findFirstByOrderByIdDesc();
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createClassClassification() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            if (classClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<AClass> classes = classRepository.findAllByTerms(term);
                for (AClass aclass :
                        classes) {
                    List<Student> students = studentRepository.findAllByAclass(aclass);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    ClassClassification classClassification = ClassClassification.builder()
                            .termId(termId)
                            .classId(aclass.getId())
                            .excellent(classificationCounter.getExcellent())
                            .good(classificationCounter.getGood())
                            .fair(classificationCounter.getFair())
                            .medium(classificationCounter.getMedium())
                            .weak(classificationCounter.getWeak())
                            .worst(classificationCounter.getWorst())
                            .build();
                    classClassificationRepository.saveAndFlush(classClassification);
                }
            }
//            Term term = termRepository.findFirstByOrderByIdDesc();
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createCourseClassification() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            if (courseClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Course> courses = courseRepository.findAllByTerms(term);
                for (Course course :
                        courses) {
                    List<Student> students = studentRepository.findAllByCourse(course);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    CourseClassification courseClassification = CourseClassification.builder()
                            .termId(termId)
                            .courseId(course.getId())
                            .excellent(classificationCounter.getExcellent())
                            .good(classificationCounter.getGood())
                            .fair(classificationCounter.getFair())
                            .medium(classificationCounter.getMedium())
                            .weak(classificationCounter.getWeak())
                            .worst(classificationCounter.getWorst())
                            .build();
                    courseClassificationRepository.saveAndFlush(courseClassification);
                }
            }
        }
//        Term term = termRepository.findFirstByOrderByIdDesc();
    }

    @Override
    @Scheduled(cron = "0 0 0 1 1,6 ?")
    public void createMajorClassification() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            if (majorClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Major> majors = majorRepository.findAllByTerms(term);
                for (Major major :
                        majors) {
                    List<Student> students = studentRepository.findAllByMajor(major);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
                    MajorClassification majorClassification = MajorClassification.builder()
                            .termId(termId)
                            .majorId(major.getId())
                            .excellent(classificationCounter.getExcellent())
                            .good(classificationCounter.getGood())
                            .fair(classificationCounter.getFair())
                            .medium(classificationCounter.getMedium())
                            .weak(classificationCounter.getWeak())
                            .worst(classificationCounter.getWorst())
                            .build();
                    majorClassificationRepository.saveAndFlush(majorClassification);
                }
            }
        }
//        Term term = termRepository.findFirstByOrderByIdDesc();
    }

    @Override
    public void createFacultyClassification() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            if (facultyClassificationRepository.findByTerm(term) == null) {
                String termId = term.getId();
                List<Student> students = studentRepository.findAllByTerms(term);
                ClassificationCounter classificationCounter = countClassified(students, termId);

                FacultyClassification facultyClassification = FacultyClassification.builder()
                        .term(term)
                        .excellent(classificationCounter.getExcellent())
                        .good(classificationCounter.getGood())
                        .fair(classificationCounter.getFair())
                        .medium(classificationCounter.getMedium())
                        .weak(classificationCounter.getWeak())
                        .worst(classificationCounter.getWorst())
                        .build();
                facultyClassificationRepository.saveAndFlush(facultyClassification);
            }
        }
    }

    @Override
    public StudentStatistic getStudentStatistic(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        List<Point> points = pointRepository.findAllByStudentId(id);
        List<StudentStatistic.AvgPoint4> avgPoint4List = new ArrayList<>();
        List<StudentStatistic.AvgPoint10> avgPoint10List = new ArrayList<>();
        List<StudentStatistic.TrainingPoint> trainingPointList = new ArrayList<>();
        String maxTerm = "0";
        for (Point point:
             points) {
            String termId = point.getTermId();
            if(termId!=null && termId.compareTo(maxTerm)>0) maxTerm = termId;
            avgPoint4List.add(StudentStatistic.AvgPoint4.builder()
                    .termId(point.getTermId())
                    .point(point.getAvgPoint4())
                    .build());
            avgPoint10List.add(StudentStatistic.AvgPoint10.builder()
                    .termId(point.getTermId())
                    .point(point.getAvgPoint10())
                    .build());
            trainingPointList.add(StudentStatistic.TrainingPoint.builder()
                    .termId(point.getTermId())
                    .point(point.getTrainingPoint())
                    .build());
        }
        Point lastPoint = pointRepository.findByStudentIdAndTermId(id, maxTerm);
        Integer creditsAcc = lastPoint.getCreditsAcc();
        Integer totalCredits = student.getMajor().getTotalCredits();
        Float accPoint4 = lastPoint.getPointAcc4();
        return StudentStatistic.builder()
                .avgPoint4List(avgPoint4List)
                .avgPoint10List(avgPoint10List)
                .trainingPointList(trainingPointList)
                .creditsAcc(creditsAcc)
                .totalCredits(totalCredits)
                .accPoint4(accPoint4)
                .build();
    }

    @Override
    public List<DropoutStatistic> getDropoutStatistic(String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public List<ClassClassification> getClassClassification(String classId, String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public List<CourseClassification> getCourseClassification(String courseId, String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public List<MajorClassification> getMajorClassification(String majorId, String fromTerm, String toTerm) {
        return null;
    }

    @Override
    public List<FacultyClassification> getFacultyClassification() {
        return null;
    }

    private ClassificationCounter countClassified(List<Student> students, String termId) {
        Integer excellent = 0;
        Integer good = 0;
        Integer fair = 0;
        Integer medium = 0;
        Integer weak = 0;
        Integer worse = 0;

        for (Student student :
                students) {
            Integer trainingPoint = pointRepository.findByStudentIdAndTermId(student.getId(), termId).getTrainingPoint();
            if (trainingPoint >= 90) {
                excellent++;
            } else if (trainingPoint >= 80) {
                good++;
            } else if (trainingPoint >= 65) {
                fair++;
            } else if (trainingPoint >= 50) {
                medium++;
            } else if (trainingPoint >= 35) {
                weak++;
            } else {
                worse++;
            }
        }

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
