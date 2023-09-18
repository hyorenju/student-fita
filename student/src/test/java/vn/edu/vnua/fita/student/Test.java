package vn.edu.vnua.fita.student;

import lombok.RequiredArgsConstructor;
import vn.edu.vnua.fita.student.util.MyUtils;

@RequiredArgsConstructor
public class Test {
    public static void main(String[] args) {
        MyUtils myUtils = new MyUtils();
        System.out.println(myUtils.test("Nguyễn Thị Minh Thu"));
    }
}
