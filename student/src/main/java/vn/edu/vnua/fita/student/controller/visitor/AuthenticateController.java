package vn.edu.vnua.fita.student.controller.visitor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.fita.student.controller.BaseController;
import vn.edu.vnua.fita.student.request.visitor.LoginRequest;
import vn.edu.vnua.fita.student.response.BaseLoginResponse;
import vn.edu.vnua.fita.student.service.visitor.AuthenticationService;


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
}
