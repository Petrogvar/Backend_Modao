package com.SpringProject.core.Services.Firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class FcmServiceImpl implements FcmService{
//
//  public FcmServiceImpl(FirebaseMessaging firebaseMessaging) {
//    this.firebaseMessaging = firebaseMessaging;
//  }
//
//  @Autowired
//  private FirebaseMessaging firebaseMessaging;
//
//  public String sendNotification(String deviceToken, String title, String body) {
//    Message message = Message.builder()
//        .setNotification(Notification.builder()
//            .setTitle(title)
//            .setBody(body)
//            .build())
//        .setToken(deviceToken)
//        .build();
//    try {
//      return firebaseMessaging.send(message);
//    } catch (Exception e) {
//      return "-1";
//    }
//  }
//}
