package vn.edu.vnua.fita.student.domain.validator;

public class ImportPointValidator {
    public static boolean validateStudentId(String studentId) {
        return studentId.matches("^[0-9]+$");
    }

    public static boolean validateStudentName(String studentName) {
        return studentName.matches("^[\\p{L} ]+$");
    }

    public static boolean validateTermId(String termId){
        return termId.matches("^[0-9]+$");
    }

//    public static boolean
}
