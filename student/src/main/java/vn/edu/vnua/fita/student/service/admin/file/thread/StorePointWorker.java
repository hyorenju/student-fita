package vn.edu.vnua.fita.student.service.admin.file.thread;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Call;
import vn.edu.vnua.fita.student.model.file.PointExcelData;
import vn.edu.vnua.fita.student.repository.jparepo.PointRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class StorePointWorker implements Callable<PointExcelData> {
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;
    private final String pointStr;
    private final int row;

    @Override
    public PointExcelData call() throws Exception {
        return null;
    }
}
