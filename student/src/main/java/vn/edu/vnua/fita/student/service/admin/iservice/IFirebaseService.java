package vn.edu.vnua.fita.student.service.admin.iservice;

import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;
import com.twilio.rest.api.v2010.account.Message;
import vn.edu.vnua.fita.student.request.student.UpdatePhoneNumberRequest;

import java.io.IOException;

public interface IFirebaseService {
    Blob uploadImage(MultipartFile file, String bucketName) throws IOException;
    String uploadFileExcel(String filePath, String bucketName) throws IOException;
    void sendOTP(UpdatePhoneNumberRequest request);
}
