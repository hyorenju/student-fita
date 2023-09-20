package vn.edu.vnua.fita.student.service.admin.file;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.service.admin.iservice.IFirebaseService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService implements IFirebaseService {
    @Override
    public Blob uploadImage(MultipartFile file, String bucketName) throws IOException {
        // Xác thực google firebase
        InputStream serviceAccountKey = getClass().getResourceAsStream("/serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountKey);

        // Tạo một tên file
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + getFileExtension(originalFileName);

        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage = storageOptions.getService();

        //Tải file lên Firebase
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        InputStream fileInputStream = file.getInputStream();
        return storage.create(blobInfo, fileInputStream);
    }

    @Override
    public String uploadFileExcel(String filePath, String bucketName) throws IOException {
        // Xác thực google firebase
        InputStream serviceAccountKey = getClass().getResourceAsStream("/serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountKey);

        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
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

    public String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}
