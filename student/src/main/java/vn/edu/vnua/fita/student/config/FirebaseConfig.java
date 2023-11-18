package vn.edu.vnua.fita.student.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}") // Đường dẫn tới file config JSON
    private String firebaseConfigPath;

    @Bean
    public GoogleCredentials getCredentials() throws IOException {
        // Xác thực google firebase
        InputStream serviceAccountKey = new FileInputStream(firebaseConfigPath);
        return GoogleCredentials.fromStream(serviceAccountKey);
    }

//    @Bean
//    public void initializeFirebaseApp() throws IOException {
//        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//        FirebaseApp.initializeApp(options);
//    }
}