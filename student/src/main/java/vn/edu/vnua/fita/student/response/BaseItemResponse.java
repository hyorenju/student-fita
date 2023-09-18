package vn.edu.vnua.fita.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseItemResponse<T> extends BaseResponse {
    private T data;
}
