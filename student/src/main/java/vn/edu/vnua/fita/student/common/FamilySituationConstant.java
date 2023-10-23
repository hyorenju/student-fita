package vn.edu.vnua.fita.student.common;

import lombok.Data;

@Data
public class FamilySituationConstant {
    public static final String NONE = "NONE";
    public static final String DIFFICULT = "DIFFICULT";
    public static final String ESPECIALLY_DIFFICULT = "ESPECIALLY_DIFFICULT";
    public static final String ETHNIC_MINORITY = "ETHNIC_MINORITY";

    public static String getSituationValue(String situation) {
        return switch (situation) {
            case NONE -> "Không";
            case DIFFICULT -> "Khó khăn";
            case ESPECIALLY_DIFFICULT -> "Đặc biệt khó khăn";
            case ETHNIC_MINORITY -> "Dân tộc thiểu số";
            default -> throw new RuntimeException("Không tìm thấy hoàn cảnh " + situation);
        };
    }
}
