package com.SpringProject.core.Services.h;

public class Uid {
  public static String getUuid(){
    int a = (int) (Math.random()*1000 +1000);
    int b = (int) (Math.random()*1000 +1000);
    int c = (int) (Math.random()*1000 +1000);
    return Integer.toString(a).substring(1) + "-" + Integer.toString(b).substring(1)
        + "-" + Integer.toString(c).substring(1);
  }
}
