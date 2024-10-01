package hotil.baemo.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream inputStream = new ClassPathResource("config/firebase/baemo-firebase-adminsdk.json").getInputStream();
        GoogleCredentials googleCredentials = getGoogleCredentials(inputStream);
        FirebaseApp firebaseApp = getFirebaseApp(googleCredentials);
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    private GoogleCredentials getGoogleCredentials(InputStream inputStream) throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream).createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));
//        googleCredentials.refreshIfExpired();
        return googleCredentials;
    }

    private FirebaseApp getFirebaseApp(GoogleCredentials googleCredentials) throws IOException {
        List<FirebaseApp> apps = FirebaseApp.getApps();
        if (apps == null || apps.isEmpty()) {
            return initializeFirebaseApp(googleCredentials);
        } else {
            return apps.stream().filter(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)).findFirst()
                .orElse(initializeFirebaseApp(googleCredentials));
        }
    }

    private FirebaseApp initializeFirebaseApp(GoogleCredentials googleCredentials) throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build();
        return FirebaseApp.initializeApp(options);
    }
}
