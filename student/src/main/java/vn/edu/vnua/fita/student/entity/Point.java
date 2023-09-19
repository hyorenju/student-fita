package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import vn.edu.vnua.fita.student.domain.validator.ImportPointValidator;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students_terms")
public class Point{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", length = 100)
    private String studentId;

    @Column(length = 200)
    private String surname;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "term_id", length = 100)
    private String termId;

    @Column(name = "average_point_10")
    private Float avgPoint10;

    @Column(name = "average_point_4")
    private Float avgPoint4;

    @Column(name = "training_point")
    private Integer trainingPoint;

    @Column(name = "credits_accumulated")
    private Integer creditsAcc;

    @Column(name = "point_accumulated_10")
    private Float pointAcc10;

    @Column(name = "point_accumulated_4")
    private Float pointAcc4;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public List<PointExcelData.ErrorDetail> validateInformationDetailError(List<PointExcelData.ErrorDetail> errorDetailList){
//        if(!ImportPointValidator.validateStudentId(studentId)){
//            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không hợp lệ").build());
//        }
//        if(!ImportPointValidator.validateTermId(termId)){
//            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Học kỳ không hợp lệ").build());
//        }
        if(!ImportPointValidator.validateDecPoint(avgPoint10)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(2).errorMsg("ĐTB10 không hợp lệ").build());
        }
        if(!ImportPointValidator.validateQuadPoint(avgPoint4)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("ĐTB4 không hợp lệ").build());
        }
        if(!ImportPointValidator.validateNaturalNum(trainingPoint)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("ĐRL không hợp lệ").build());
        }
        if(!ImportPointValidator.validateNaturalNum(creditsAcc)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("TCTL không hợp lệ").build());
        }
        if(!ImportPointValidator.validateDecPoint(pointAcc10)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(6).errorMsg("ĐTBTL10 không hợp lệ").build());
        }
        if(!ImportPointValidator.validateQuadPoint(pointAcc4)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(7).errorMsg("ĐTBTL4 không hợp lệ").build());
        }

        return errorDetailList;
    }
}
