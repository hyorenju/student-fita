package vn.edu.vnua.fita.student.controller.admin.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.service.admin.statistic.StatisticService;

@RestController
@RequestMapping("admin/statistic")
@RequiredArgsConstructor
public class StatisticController extends BaseController {
    private final StatisticService statisticService;

    @PostMapping("student/{id}")
    public ResponseEntity<?> getStudentStatistic(@PathVariable String id){
        StudentStatistic response = statisticService.getStudentStatistic(id);
        return buildItemResponse(response);
    }

    
    
    
    
    // Dưới đây là API periodic creating, đề nghị front-end dev không sử dụng dưới mọi hình thức
    @PostMapping("dropout")
    public ResponseEntity<?> createDropoutStatistic(){
        statisticService.createDropoutStatisticPeriodic();
        return buildItemResponse("Tạo thành công");
    }
    @PostMapping("class-classification")
    public ResponseEntity<?> createClassClassification(){
        statisticService.createClassClassificationPeriodic();
        return buildItemResponse("Tạo thành công");
    }
    @PostMapping("course-classification")
    public ResponseEntity<?> createCourseClassification(){
        statisticService.createCourseClassificationPeriodic();
        return buildItemResponse("Tạo thành công");
    }
    @PostMapping("major-classification")
    public ResponseEntity<?> createMajorClassification(){
        statisticService.createMajorClassificationPeriodic();
        return buildItemResponse("Tạo thành công");
    }
    @PostMapping("faculty-classification")
    public ResponseEntity<?> createFacultyClassification(){
        statisticService.createFacultyClassificationPeriodic();
        return buildItemResponse("Tạo thành công");
    }
}
