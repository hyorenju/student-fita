package vn.edu.vnua.fita.student.dto;

import lombok.Data;

@Data
public class ClassDTO {
    private String id;
    private String name;
    private MonitorDTO monitor;
    private MonitorDTO viceMonitor;
    private MonitorDTO secretary;
    private MonitorDTO deputySecretary;
}
