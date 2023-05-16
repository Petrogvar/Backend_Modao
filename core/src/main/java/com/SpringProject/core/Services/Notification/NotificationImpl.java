package com.SpringProject.core.Services.Notification;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Services.Firebase.FcmService;

import java.sql.Timestamp;
import org.springframework.stereotype.Service;

@Service
public class NotificationImpl implements Notification{
  private final FcmService fcmService;

  public NotificationImpl(FcmService fcmService) {
    this.fcmService = fcmService;
  }

  public void newNotificationFriends(User userTo, User userFrom){
      if (userTo.getToken().getRegistrationToken() == null || userTo.getToken().getTime().getTime() + 60000L *60*24*30 >
          (new Timestamp(System.currentTimeMillis())).getTime()){
        return;
      }
      String title = "1";
      String body = "123";
    fcmService.sendNotification(userTo.getToken().getRegistrationToken(), title, body);
  }

  public void newNotificationEvent(User userTo,  Event event){
    if (userTo.getToken().getRegistrationToken() == null || userTo.getToken().getTime().getTime() + 60000L *60*24*30 >
        (new Timestamp(System.currentTimeMillis())).getTime()){
      return;
    }
    String title = "2";
    String body = "123";
    fcmService.sendNotification(userTo.getToken().getRegistrationToken(), title, body);
  }


   public void newNotificationGroup(User userTo, User userFrom, Group group){
    if (userTo.getToken().getRegistrationToken() == null || userTo.getToken().getTime().getTime() + 60000L *60*24*30 >
        (new Timestamp(System.currentTimeMillis())).getTime()){
      return;
    }
    String title = "3";
    String body = "123";
     fcmService.sendNotification(userTo.getToken().getRegistrationToken(), title, body);
   }
}
