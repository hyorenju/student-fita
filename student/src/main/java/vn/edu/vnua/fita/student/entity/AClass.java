package vn.edu.vnua.fita.student.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import vn.edu.vnua.fita.student.domain.validator.ImportClassValidator;
import vn.edu.vnua.fita.student.domain.validator.ImportStudentStatusValidator;
import vn.edu.vnua.fita.student.model.file.ClassExcelData;
import vn.edu.vnua.fita.student.model.file.ExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class AClass {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String name;

    @OneToOne
    @JoinColumn(name = "monitor")
    private Student monitor;

    @OneToOne
    @JoinColumn(name = "vice_monitor")
    private Student viceMonitor;

    @OneToOne
    @JoinColumn(name = "secretary")
    private Student secretary;

    @OneToOne
    @JoinColumn(name = "deputy_secretary")
    private Student deputySecretary;

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL)
    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "classes_terms",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;

    public List<ClassExcelData.ErrorDetail> validateInformationDetailError(List<ClassExcelData.ErrorDetail> errorDetailList){
        if(!ImportClassValidator.validateId(id)){
            errorDetailList.add(StudentStatusExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã lớp không hợp lệ").build());
        }

        return errorDetailList;
    }
}
