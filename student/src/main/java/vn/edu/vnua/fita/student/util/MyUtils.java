package vn.edu.vnua.fita.student.util;
import vn.edu.vnua.fita.student.common.DateTimeConstant;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyUtils {

    public static Timestamp convertTimestampFromString(String inputDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateTimeConstant.DATE_FORMAT);
            simpleDateFormat.setLenient(false);
            return new Timestamp(simpleDateFormat.parse(inputDate).getTime());
        } catch(ParseException e) {
            return new Timestamp(0);
        }
    }

    public static String formatDobToPassword(String inputDob) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate date = LocalDate.parse(inputDob, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(outputFormatter);
    }

    public static String convertTimestampToString(Timestamp inputDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(inputDate);
    }

    public static String parseFloatToString(Float input) {
        try {
            return String.format("%.2f", input);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public static Float parseFloatFromString(String input) {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public static Integer parseIntegerFromString(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            return null;
        }
    }

    public String test(String input){
        String year = input.substring(0, input.length()-1);
        String term = input.substring(input.length()-1);
        return term;
    }
}
