package vn.edu.vnua.fita.student.common;

import lombok.Data;

@Data
public class UserIdentifyPatternConstant {
    public static final String STUDENT_ID_PATTERN = "^[0-9]+$";
    public static final String ADMIN_ID_PATTERN = "^[a-zA-Z][a-zA-Z0-9]*$";
}
