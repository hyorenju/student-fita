package vn.edu.vnua.fita.student.controller.visitor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.fita.student.common.ErrorCodeDefinitions;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.entity.StudentRefresher;
import vn.edu.vnua.fita.student.request.visitor.LoginRequest;
import vn.edu.vnua.fita.student.request.visitor.RefreshTokenRequest;
import vn.edu.vnua.fita.student.response.BaseLoginResponse;
import vn.edu.vnua.fita.student.response.BaseResponse;
import vn.edu.vnua.fita.student.service.visitor.AuthenticationService;

import java.io.IOException;


@RestController
@RequestMapping("visitor")
@RequiredArgsConstructor
public class AuthenticateController extends BaseController {
    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        BaseLoginResponse response = authenticationService.authenticateUser(request.getId(), request.getPassword());
        return buildItemResponse(response);
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) throws IOException {
        BaseLoginResponse response = authenticationService.verifyExpiration(request.getRefreshToken());
        if (response == null) {
            return buildServerErrorResponse(ErrorCodeDefinitions.REFRESH_EXPIRED, ErrorCodeDefinitions.getErrMsg(ErrorCodeDefinitions.REFRESH_EXPIRED));
        } else {
            return buildItemResponse(response);
        }
    }
}
