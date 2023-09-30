package vn.edu.vnua.fita.student.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.model.dto.ClassClassificationDTO;
import vn.edu.vnua.fita.student.model.dto.CourseClassificationDTO;
import vn.edu.vnua.fita.student.model.dto.FacultyClassificationDTO;
import vn.edu.vnua.fita.student.model.dto.MajorClassificationDTO;
import vn.edu.vnua.fita.student.model.entity.FacultyClassification;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.request.admin.statistic.GetStatisticRequest;
import vn.edu.vnua.fita.student.service.admin.statistic.StatisticService;

import java.util.List;

@RestController
@RequestMapping("admin/statistic")
@RequiredArgsConstructor
public class StatisticController extends BaseController {
    private final StatisticService statisticService;
    private final ModelMapper modelMapper;

    @PostMapping("student/{id}")
    public ResponseEntity<?> getStudentStatistic(@PathVariable String id){
        StudentStatistic response = statisticService.getStudentStatistic(id);
        return buildItemResponse(response);
    }

    @PostMapping("class/{id}")
    public ResponseEntity<?> getClassStatistic(@PathVariable String id, @Valid @RequestBody GetStatisticRequest request){
        List<ClassClassificationDTO> response = statisticService.getClassClassification(id, request).stream().map(
                classClassification -> modelMapper.map(classClassification, ClassClassificationDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("course/{id}")
    public ResponseEntity<?> getCourseStatistic(@PathVariable String id, @Valid @RequestBody GetStatisticRequest request){
        List<CourseClassificationDTO> response = statisticService.getCourseClassification(id, request).stream().map(
                courseClassification -> modelMapper.map(courseClassification, CourseClassificationDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("major/{id}")
    public ResponseEntity<?> getMajorStatistic(@PathVariable String id, @Valid @RequestBody GetStatisticRequest request){
        List<MajorClassificationDTO> response = statisticService.getMajorClassification(id, request).stream().map(
                majorClassification -> modelMapper.map(majorClassification, MajorClassificationDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    @PostMapping("faculty")
    public ResponseEntity<?> getFacultyStatistic(@Valid @RequestBody GetStatisticRequest request){
        List<FacultyClassificationDTO> response = statisticService.getFacultyClassification(request).stream().map(
                facultyClassification -> modelMapper.map(facultyClassification, FacultyClassificationDTO.class)
        ).toList();
        return buildListItemResponse(response, response.size());
    }

    
    
    
    
    // Dưới đây là API periodic creating, đề nghị front-end dev không sử dụng dưới mọi hình thức
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
