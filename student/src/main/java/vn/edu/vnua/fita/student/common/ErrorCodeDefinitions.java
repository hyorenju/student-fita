package vn.edu.vnua.fita.student.common;

public class ErrorCodeDefinitions {
    public static final int OTHER = 1;
    public static final int VALIDATION_ERROR = 2;
    public static final int SERVER_ERROR = 500;
    public static final int BAD_REQUEST = 400;
    public static final int PERMISSION_INVALID = 3;
    public static final int TOKEN_INVALID = 4;
    public static final int TOKEN_REQUIRED = 5;
    public static final int TOKEN_EXPIRED = 6;

    public static String getErrMsg(int code) {
        return switch (code) {
            case OTHER -> "Các lỗi khác";
            case PERMISSION_INVALID -> "Bạn không có quyền gọi đến chức năng này";
            case VALIDATION_ERROR -> "Tham số không hợp lệ";
            case SERVER_ERROR -> "Lỗi hệ thống";
            case BAD_REQUEST -> "Bad request";
            case TOKEN_REQUIRED -> "Yêu cầu token";
            case TOKEN_INVALID -> "Token không hợp lệ";
            case TOKEN_EXPIRED -> "Token hết hạn";
            default -> "Các lỗi khác";
        };
    }
}
