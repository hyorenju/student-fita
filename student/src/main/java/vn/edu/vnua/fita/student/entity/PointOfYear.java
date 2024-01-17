package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import vn.edu.vnua.fita.student.domain.validator.ImportPointValidator;
import vn.edu.vnua.fita.student.model.file.PointAnnualExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "point_annual")
public class PointOfYear{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "school_year")
    private SchoolYear year;

    @Column(name = "average_point_10")
    private Float avgPoint10;

    @Column(name = "average_point_4")
    private Float avgPoint4;

    @Column(name = "training_point")
    private Integer trainingPoint;

    @Column(name = "point_accumulated_10")
    private Float pointAcc10;

    @Column(name = "point_accumulated_4")
    private Float pointAcc4;

    @Column(name = "credits_registered")
    private Integer creditsRegistered;

    @Column(name = "credits_passed")
    private Integer creditsPassed;

    @Column(name = "credits_not_passed")
    private Integer creditsNotPassed;

    @Column(name = "credits_accumulated")
    private Integer creditsAcc;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public List<PointAnnualExcelData.ErrorDetail> validateInformationDetailError(List<PointAnnualExcelData.ErrorDetail> errorDetailList){
//        if(!ImportPointValidator.validateStudentId(studentId)){
//            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã sv không hợp lệ").build());
//        }
//        if(!ImportPointValidator.validateSchoolYear(year)){
//            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Năm học không hợp lệ").build());
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
        if(!ImportPointValidator.validateNaturalNum(creditsRegistered)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(8).errorMsg("TC ĐK không hợp lệ").build());
        }
        if(!ImportPointValidator.validateNaturalNum(creditsPassed)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(9).errorMsg("TC đạt không hợp lệ").build());
        }
        if(!ImportPointValidator.validateNaturalNum(creditsNotPassed)){
            errorDetailList.add(PointExcelData.ErrorDetail.builder().columnIndex(10).errorMsg("TC ko đạt không hợp lệ").build());
        }

        return errorDetailList;
    }
}
