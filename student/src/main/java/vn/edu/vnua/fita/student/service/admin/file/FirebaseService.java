package vn.edu.vnua.fita.student.service.admin.file;

import com.google.cloud.storage.*;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.config.FirebaseConfig;
import vn.edu.vnua.fita.student.config.TwilioConfig;
import vn.edu.vnua.fita.student.service.admin.iservice.IFirebaseService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FirebaseService implements IFirebaseService {
    private final FirebaseConfig firebaseConfig;
    private final TwilioConfig twilioConfig;


    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String phoneNumber;

    @Override
    public Blob uploadImage(MultipartFile file, String bucketName) throws IOException {
        // Tạo một tên file
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + getFileExtension(originalFileName);

        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(firebaseConfig.getCredentials()).build();
        Storage storage = storageOptions.getService();

        //Tải file lên Firebase
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        InputStream fileInputStream = file.getInputStream();
        return storage.create(blobInfo, fileInputStream);
    }

    @Override
    public String uploadFileExcel(String filePath, String bucketName) throws IOException {
        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(firebaseConfig.getCredentials()).build();
        Storage storage = storageOptions.getService();

        // Lấy tên file
        String fileName = filePath.split("/")[filePath.split("/").length - 1];

        // Tìm kiếm và xóa file từ Firebase Storage
        Bucket bucket = storage.get(bucketName);
        for (Blob blob : bucket.list().iterateAll()) {
            if (blob.getName().equals(fileName)) {
                blob.delete();
            }
        }

        // Tải lên tệp tin vào Firebase Storage
        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        FileInputStream fileInputStream = new FileInputStream(filePath);

        return storage.create(blobInfo, fileInputStream)
                .signUrl(FirebaseExpirationTimeConstant.EXPIRATION_TIME, TimeUnit.MILLISECONDS)
                .toString();
    }

    @Override
    public void sendOTP(String phoneNumber) {
        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        String otp = generateOTP();
        String otpMessage = "Bạn đã yêu cầu cập nhật số điện thoại. Mã OTP của bạn là " + otp + ", vui lòng không chia sẻ mã này cho bất kỳ ai. Nếu bạn không yêu cầu điều này, vui lòng bỏ qua tin nhắn.";
        Message message = Message.creator(to, from, otpMessage).create();

        // Log thông tin gửi OTP tại đây (thông qua logger)
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
