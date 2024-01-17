package vn.edu.vnua.fita.student.service.admin.file.thread.aclass;

import lombok.AllArgsConstructor;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Status;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.file.ClassExcelData;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.model.file.StudentStatusExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StatusRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
public class StoreClassWorker implements Callable<ClassExcelData> {
    private final String classStr;
    private final int row;


    @Override
    public ClassExcelData call() throws Exception {
        ClassExcelData classExcelData = new ClassExcelData();

        if (!classStr.isEmpty()) {
            String[] infoList = classStr.strip().split(",");
            String classId = infoList[0].strip();
            String name = infoList[1].strip();

            AClass aClass = AClass.builder()
                    .id(classId)
                    .name(name)
                    .build();

            List<ClassExcelData.ErrorDetail> errorDetailList = aClass.validateInformationDetailError(new CopyOnWriteArrayList<>());

            classExcelData.setAClass(aClass);
            if (!errorDetailList.isEmpty()) {
                classExcelData.setErrorDetailList(errorDetailList);
                classExcelData.setValid(false);
            }
            classExcelData.setRowIndex(row);
        }

        return classExcelData;
    }
}
