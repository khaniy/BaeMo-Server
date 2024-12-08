package hotil.baemo.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {

    @Bean
    @Profile("local")
    FirebaseMessaging localFirebaseMessaging() throws IOException {
        return firebaseMessaging("config/firebase/baemo-firebase-adminsdk-local.json");
    }

    @Bean
    @Profile("dev")
    FirebaseMessaging devFirebaseMessaging() throws IOException {
        return firebaseMessaging("config/firebase/baemo-firebase-adminsdk-dev.json");
    }

    @Bean
    @Profile("prod")
    FirebaseMessaging prodFirebaseMessaging() throws IOException {
        return firebaseMessaging("config/firebase/baemo-firebase-adminsdk-prod.json");
    }


    private FirebaseMessaging firebaseMessaging(String path) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
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
            .setProjectId("baemo-4e1d1")
            .build();
        return FirebaseApp.initializeApp(options);
    }
}
