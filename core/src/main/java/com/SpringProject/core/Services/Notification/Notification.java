package com.SpringProject.core.Services.Notification;

import com.SpringProject.core.Entity.Event;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;

public interface Notification {
   void newNotificationFriends(User userTo, User userFrom);
   void newNotificationEvent(User userTo,  Event event);
   void newNotificationGroup(User userTo,  User userFrom, Group group);
}
