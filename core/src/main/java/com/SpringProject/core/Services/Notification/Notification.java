package com.SpringProject.core.Services.Notification;

import com.SpringProject.core.Entity.User;

public interface Notification {
  public String newNotificationFriends(User userTo, User userFrom);
}
