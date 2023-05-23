package com.SpringProject.core.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {

    FileInputStream serviceAccount =
        new FileInputStream( "/root/serviceAccountKey.json" /*"C:/Users/1/Backend_Modao/core/src/main/resources/serviceAccountKey.json"*/);

    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

    return FirebaseApp.initializeApp(options);
  }

  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {
    return FirebaseMessaging.getInstance(firebaseApp());
  }
}
