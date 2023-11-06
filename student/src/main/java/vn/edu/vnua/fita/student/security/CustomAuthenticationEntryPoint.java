package vn.edu.vnua.fita.student.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.response.BaseResponse;

import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setFailed(ErrorCodeDefinitions.TOKEN_INVALID, ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.TOKEN_INVALID));
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, baseResponse);
        responseStream.flush();
    }
}
