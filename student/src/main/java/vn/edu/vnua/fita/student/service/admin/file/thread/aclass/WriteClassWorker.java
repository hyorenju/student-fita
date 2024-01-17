package vn.edu.vnua.fita.student.service.admin.file.thread.aclass;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import vn.edu.vnua.fita.student.entity.AClass;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class WriteClassWorker implements Callable<Void> {
    private Row row;
    private AClass aClass;

    @Override
    public Void call() throws Exception {
        Student monitor = aClass.getMonitor();
        Student viceMonitor = aClass.getViceMonitor();
        Student secretary = aClass.getSecretary();
        Student deputySecretary = aClass.getDeputySecretary();

        row.createCell(0).setCellValue(aClass.getId());
        row.createCell(1).setCellValue(aClass.getName());
        row.createCell(2).setCellValue(monitor.getId());
        row.createCell(3).setCellValue(monitor.getSurname() + monitor.getLastName());
        row.createCell(4).setCellValue(monitor.getPhoneNumber());
        row.createCell(5).setCellValue(viceMonitor.getId());
        row.createCell(6).setCellValue(viceMonitor.getSurname() + viceMonitor.getLastName());
        row.createCell(7).setCellValue(viceMonitor.getPhoneNumber());
        row.createCell(8).setCellValue(secretary.getId());
        row.createCell(9).setCellValue(secretary.getSurname() + secretary.getLastName());
        row.createCell(10).setCellValue(secretary.getPhoneNumber());
        row.createCell(11).setCellValue(deputySecretary.getId());
        row.createCell(12).setCellValue(deputySecretary.getSurname() + deputySecretary.getLastName());
        row.createCell(13).setCellValue(deputySecretary.getPhoneNumber());
        return null;
    }
}
