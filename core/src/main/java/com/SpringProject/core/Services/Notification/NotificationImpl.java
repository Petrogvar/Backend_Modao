package com.SpringProject.core.Services.Notification;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
//import com.SpringProject.core.Services.Firebase.FcmService;

import com.SpringProject.core.Services.Firebase.FcmService;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;


@Service
public class NotificationImpl implements Notification {

  private final FcmService fcmService;


  public NotificationImpl(FcmService fcmService) {
    this.fcmService = fcmService;
  }

  public void newNotificationFriends(User userTo, User userFrom) {
    if (userTo.getToken().getRegistrationToken() == null
        || userTo.getToken().getTime().getTime() + 60000L * 60 * 24 * 30 <
        (new Timestamp(System.currentTimeMillis())).getTime()) {
      return;
    }
    String title = "Friend invitation";
    String body = userFrom.getUsername() + " invited you to friends";

    fcmService.sendNotification1(userTo.getToken().getRegistrationToken(), title, body, "action",
        "move to notification");
  }

  public void newNotificationEvent(User userTo, Event event) {
    if (userTo.getToken().getRegistrationToken() == null
        || userTo.getToken().getTime().getTime() + 60000L * 60 * 24 * 30 <
        (new Timestamp(System.currentTimeMillis())).getTime()) {
      return;
    }
    String title = "Event confirmation";
    String body = event.getEventName() + " event requires confirmation";

    fcmService.sendNotification2(userTo.getToken().getRegistrationToken(), title, body, "action",
        "move to data confirmation",
        "groupID",
        event.getGroup().getId().toString());
  }


  public void newNotificationGroup(User userTo, User userFrom, Group group) {
    if (userTo.getToken().getRegistrationToken() == null
        || userTo.getToken().getTime().getTime() + 60000L * 60 * 24 * 30 <
        (new Timestamp(System.currentTimeMillis())).getTime()) {
      return;
    }
    String title = "Group invitation";
    String body = userFrom.getUsername() + " invited you to the group " + group.getGroupName();
    fcmService.sendNotification1(userTo.getToken().getRegistrationToken(), title, body, "action",
        "move to notification");
  }
}