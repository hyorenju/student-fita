package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import vn.edu.vnua.fita.student.domain.validator.ImportPointValidator;
import vn.edu.vnua.fita.student.domain.validator.ImportStudentStatusValidator;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students_statuses")
public class StudentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column
    private Timestamp time;

    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @Column(length = 200)
    private String note;

    public List<StudentStatusExcelData.ErrorDetail> validateInformationDetailError(List<StudentStatusExcelData.ErrorDetail> errorDetailList){
        if(!ImportStudentStatusValidator.validateTime(time)){
            errorDetailList.add(StudentStatusExcelData.ErrorDetail.builder().columnIndex(2).errorMsg("Thời gian không hợp lệ").build());
        }

        return errorDetailList;
    }
}
