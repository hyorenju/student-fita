package vn.edu.vnua.fita.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;

@RestController
@RequestMapping("send-otp")
@RequiredArgsConstructor
public class ForTestingController extends BaseController{
    private final FirebaseService firebaseService;

    @PostMapping()
    public ResponseEntity<?> sendNotification(@RequestParam String phoneNumber) {
        firebaseService.sendOTP(phoneNumber);
        String message = "Thành công";
        return buildItemResponse(message);
    }
}
