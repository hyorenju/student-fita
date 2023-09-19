package vn.edu.vnua.fita.student.domain.validator;

public class ImportPointValidator {
//    public static boolean validateStudentId(String studentId) {
//        return studentId.matches("^[0-9]+$");
//    }
//
//    public static boolean validateStudentName(String studentName) {
//        return studentName.matches("^[\\p{L} ]+$");
//    }
//
//    public static boolean validateTermId(String termId){
//        return termId.matches("^[0-9]+$");
//    }

    public static boolean validateQuadPoint(Float quadPoint){
        return (quadPoint != null && quadPoint >= 0 && quadPoint <= 4);
    }

    public static boolean validateDecPoint(Float decPoint){
        return (decPoint != null && decPoint >=0 && decPoint <=10);
    }

    public static boolean validateNaturalNum(Integer naturalNum){
        return (naturalNum != null && naturalNum >= 0);
    }
}
