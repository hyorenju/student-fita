package vn.edu.vnua.fita.student.domain.validator;

import java.sql.Timestamp;

public class ImportStudentStatusValidator {
    public static boolean validateTime(Timestamp dob) {
        return !dob.equals(new Timestamp(0));
    }
}
