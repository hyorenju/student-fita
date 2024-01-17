package vn.edu.vnua.fita.student.domain.validator;

import vn.edu.vnua.fita.student.common.IdentifyPatternConstant;

import java.sql.Timestamp;

public class ImportClassValidator {
    public static boolean validateId(String id) {
        return id.matches(IdentifyPatternConstant.CLASS_ID_PATTERN);
    }
}
