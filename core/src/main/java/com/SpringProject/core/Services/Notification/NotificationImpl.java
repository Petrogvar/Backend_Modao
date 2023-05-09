package com.SpringProject.core.Services.Notification;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Services.Firebase.FcmService;

import java.sql.Timestamp;


public class NotificationImpl implements Notification{
  private final FcmService fcmService;

  public NotificationImpl(FcmService fcmService) {
    this.fcmService = fcmService;
  }

  public String newNotificationFriends(User userTo, User userFrom){
      if (userTo.getToken().getRegistrationToken() == null || userTo.getToken().getTime().getTime() + 60000L *60*24*30 >
          (new Timestamp(System.currentTimeMillis())).getTime()){
        return "-1";
      }
      String title = "";
      String body = "";
      return fcmService.sendNotification(userTo.getToken().getRegistrationToken(), title, body);
    }
}
