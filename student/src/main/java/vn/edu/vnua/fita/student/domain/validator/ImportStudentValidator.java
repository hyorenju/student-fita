package vn.edu.vnua.fita.student.domain.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.apache.poi.ss.formula.functions.T;

import java.sql.Timestamp;

public class ImportStudentValidator {
    public static boolean validateId(String id) {
        return id.matches("^[0-9]+$");
    }

    public static boolean validateName(String name){
        return name.matches("^[\\p{L} ]+$");
    }

//    public static boolean validateCourse(String courseId){
//        return courseId.matches("^\\d+$");
//    }
//
//    public static boolean validateMajor(String majorId){
//        return majorId.matches("^[A-Z]+$");
//    }
//
//    public static boolean validateClass(String classId){
//        return classId.matches("^K\\d+[A-Z]+$");
//    }

    public static boolean validateDob(Timestamp dob) {
        return !dob.equals(new Timestamp(0));
    }

    public static boolean validateGender(String gender) {
        return gender.toLowerCase().matches("^(nam|nữ)$");
    }

    public static boolean validatePhoneNumber(String phoneNumber){
        return new PhoneNumberValidator().validatePhoneNumber(phoneNumber);
    }

    public static boolean validateEmail(String email){
        return new EmailValidator().validateEmail(email);
    }

    public static boolean validateHomeTown(String homeTown){
        return homeTown.matches("^[\\p{L}[0-9] ,.-]+$");
    }
}
