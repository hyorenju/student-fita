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

        String monitorId = monitor != null ? monitor.getId() : "";
        String monitorName = monitor != null ? monitor.getSurname() + monitor.getLastName() : "";
        String monitorPhone = monitor != null ? monitor.getPhoneNumber() : "";

        String viceMonitorId = viceMonitor != null ? viceMonitor.getId() : "";
        String viceMonitorName = viceMonitor != null ? viceMonitor.getSurname() + viceMonitor.getLastName() : "";
        String viceMonitorPhone = viceMonitor != null ? viceMonitor.getPhoneNumber() : "";

        String secretaryId = secretary != null ? secretary.getId() : "";
        String secretaryName = secretary != null ? secretary.getSurname() + secretary.getLastName() : "";
        String secretaryPhone = secretary != null ? secretary.getPhoneNumber() : "";

        String deputySecretaryId = deputySecretary != null ? deputySecretary.getId() : "";
        String deputySecretaryName = deputySecretary != null ? deputySecretary.getSurname() + deputySecretary.getLastName() : "";
        String deputySecretaryPhone = deputySecretary != null ? deputySecretary.getPhoneNumber() : "";

        row.createCell(0).setCellValue(aClass.getId());
        row.createCell(1).setCellValue(aClass.getName());
        row.createCell(2).setCellValue(monitorId);
        row.createCell(3).setCellValue(monitorName);
        row.createCell(4).setCellValue(monitorPhone);
        row.createCell(5).setCellValue(viceMonitorId);
        row.createCell(6).setCellValue(viceMonitorName);
        row.createCell(7).setCellValue(viceMonitorPhone);
        row.createCell(8).setCellValue(secretaryId);
        row.createCell(9).setCellValue(secretaryName);
        row.createCell(10).setCellValue(secretaryPhone);
        row.createCell(11).setCellValue(deputySecretaryId);
        row.createCell(12).setCellValue(deputySecretaryName);
        row.createCell(13).setCellValue(deputySecretaryPhone);
        return null;
    }
}
