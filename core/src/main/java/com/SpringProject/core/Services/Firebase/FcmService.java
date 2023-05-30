package com.SpringProject.core.Services.Firebase;

public interface FcmService {
  public String sendNotification1(String deviceToken, String title, String body, String key, String value);

  public String sendNotification2(String deviceToken, String title, String body, String key1, String value1, String key2, String value2);

}