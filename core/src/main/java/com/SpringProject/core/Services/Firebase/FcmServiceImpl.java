package com.SpringProject.core.Services.Firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import liquibase.pro.packaged.S;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FcmServiceImpl implements FcmService{

  public FcmServiceImpl(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }

  @Autowired
  private FirebaseMessaging firebaseMessaging;

  public String sendNotification1(String deviceToken, String title, String body, String key, String value) {
    Message message = Message.builder()
        .putData(key, value)
        .setNotification(Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build())
        .setToken(deviceToken)
        .build();
    try {
      return firebaseMessaging.send(message);
    } catch (Exception e) {
      return "-1";
    }
  }

  public String sendNotification2(String deviceToken, String title, String body, String key1, String value1, String key2, String value2) {
    Message message = Message.builder()
        .putData(key1, value1)
        .putData(key2, value2)
        .setNotification(Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build())
        .setToken(deviceToken)
        .build();
    try {
      return firebaseMessaging.send(message);
    } catch (Exception e) {
      return "-1";
    }
  }
}
