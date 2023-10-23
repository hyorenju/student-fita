package vn.edu.vnua.fita.student;

import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.util.MyUtils;

import java.text.ParseException;

@RequiredArgsConstructor
public class Test {
    public static void main(String[] args) throws ParseException {
        MyUtils myUtils = new MyUtils();
        System.out.println(myUtils.test("2/13/2001"));
    }
}
