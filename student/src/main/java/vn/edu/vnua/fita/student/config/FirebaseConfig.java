package vn.edu.vnua.fita.student.config;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.name}") // Đường dẫn tới file config JSON
    private String firebaseConfigName;

    @Bean
    public GoogleCredentials getCredentials() throws IOException {
        // Xác thực google firebase
        try (InputStream serviceAccountKey = new ClassPathResource(firebaseConfigName).getInputStream()){
            return GoogleCredentials.fromStream(serviceAccountKey);
        } catch (Exception e) {
            throw e;
        }
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