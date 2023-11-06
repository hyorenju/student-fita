package vn.edu.vnua.fita.student.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.response.BaseResponse;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access denied error: {}", accessDeniedException.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setFailed(ErrorCodeDefinitions.PERMISSION_INVALID, ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.PERMISSION_INVALID));
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, baseResponse);
        responseStream.flush();
    }
}