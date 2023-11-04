package vn.edu.vnua.fita.student;

import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class Test {
    public static void main(String[] args) throws ParseException {
        MyUtils myUtils = new MyUtils();
        System.out.println(myUtils.test(MyUtils.convertTimestampFromString("12/8/2020")));
    }
}
