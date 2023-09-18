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

    public String test(String input){
        int lastSpaceIndex = input.lastIndexOf(" ");

        String surname = input.substring(0, lastSpaceIndex);
        String lastName = input.substring(lastSpaceIndex + 1);
        return surname;
    }
}
