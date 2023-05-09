package com.SpringProject.core.Services.Firebase;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface FcmService {
  public String sendNotification(String deviceToken, String title, String body);
}