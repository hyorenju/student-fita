package vn.edu.vnua.fita.student.service.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.model.dto.StudentDTO;
import vn.edu.vnua.fita.student.model.entity.*;
import vn.edu.vnua.fita.student.model.statistic.ClassificationCounter;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.repository.jparepo.*;
import vn.edu.vnua.fita.student.service.admin.iservice.IStatisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {
    private final StudentRepository studentRepository;
    private final TermRepository termRepository;
    private final PointRepository pointRepository;
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
    @Scheduled(cron = "0 0 0 4 1,6 ?")
    public void createClassClassificationPeriodic() {
//        List<Term> terms = termRepository.findAll();
//        for (Term term :
//                terms) {
            Term term = termRepository.findFirstByOrderByIdDesc();
            if (classClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<AClass> classes = classRepository.findAll();
                for (AClass aclass :
                        classes) {
                    List<Student> students = studentRepository.findAllByAclass(aclass);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
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
                    classClassificationRepository.saveAndFlush(classClassification);
                }
            }
//        }
    }

    @Override
    @Scheduled(cron = "0 0 0 3 1,6 ?")
    public void createCourseClassificationPeriodic() {
//        List<Term> terms = termRepository.findAll();
//        for (Term term :
//                terms) {
            Term term = termRepository.findFirstByOrderByIdDesc();
            if (courseClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Course> courses = courseRepository.findAll();
                for (Course course :
                        courses) {
                    List<Student> students = studentRepository.findAllByCourse(course);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
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
                    courseClassificationRepository.saveAndFlush(courseClassification);
                }
            }
//        }
    }

    @Override
    @Scheduled(cron = "0 0 0 2 1,6 ?")
    public void createMajorClassificationPeriodic() {
//        List<Term> terms = termRepository.findAll();
//        for (Term term :
//                terms) {
            Term term = termRepository.findFirstByOrderByIdDesc();
            if (majorClassificationRepository.findByTermId(term.getId()) == null) {
                String termId = term.getId();
                List<Major> majors = majorRepository.findAll();
                for (Major major :
                        majors) {
                    List<Student> students = studentRepository.findAllByMajor(major);
                    ClassificationCounter classificationCounter = countClassified(students, termId);
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
                    majorClassificationRepository.saveAndFlush(majorClassification);
                }
            }
//        }
    }

    @Override
    @Scheduled(cron = "0 0 12 1 1,6 ?")
    public void createFacultyClassificationPeriodic() {
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            FacultyClassification facultyClassification = new FacultyClassification();
//            Term term = termRepository.findFirstByOrderByIdDesc();
            if (facultyClassificationRepository.findByTerm(term) == null) {
                String termId = term.getId();
                Integer dropoutWithoutPermission = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 2).size();
                Integer dropoutWithPermission = studentStatusRepository
                        .findAllByTermIdAndStatusId(termId, 3).size();

                facultyClassification.setTerm(term);
                facultyClassification.setDropoutWithoutPermission(dropoutWithoutPermission);
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
            facultyClassificationRepository.saveAndFlush(facultyClassification);
        }
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
        Point lastPoint = points.get(points.size()-1);
        Integer creditsAcc = lastPoint.getCreditsAcc();
        Integer totalCredits = student.getMajor().getTotalCredits();
        Float accPoint4 = lastPoint.getPointAcc4();
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
            Optional<Point> pointOptional = pointRepository.findByStudentIdAndTermId(student.getId(), termId);
            if(pointOptional.isPresent()){
                Integer trainingPoint = pointOptional.get().getTrainingPoint();
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
