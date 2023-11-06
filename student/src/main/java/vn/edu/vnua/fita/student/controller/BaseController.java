package vn.edu.vnua.fita.student.controller;

import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.response.BaseItemResponse;
import vn.edu.vnua.fita.student.response.BaseListItemResponse;
import vn.edu.vnua.fita.student.response.BasePageItemResponse;
import org.springframework.http.ResponseEntity;
import vn.edu.vnua.fita.student.response.ServerErrorResponse;

import java.util.List;

public class BaseController {

    protected <T> ResponseEntity<?> buildItemResponse(T data) {
        BaseItemResponse<T> response = new BaseItemResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildListItemResponse(List<T> data, long total) {
        BaseListItemResponse<T> response = new BaseListItemResponse<>();
        response.setResult(data, total);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildPageItemResponse(long page, long limit, long totalElements, List<T> items){
        BasePageItemResponse<T> response = new BasePageItemResponse<>();
        response.setPageItems(page, limit, totalElements, items);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildServerErrorResponse(int code, String message) {
        ServerErrorResponse<T> response = new ServerErrorResponse<>();
        response.setFailed(code, message);
        return ResponseEntity.internalServerError().body(response);
    }
}
