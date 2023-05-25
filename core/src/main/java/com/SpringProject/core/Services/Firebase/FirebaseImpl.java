package com.SpringProject.core.Services.Firebase;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
//public class FirebaseImpl implements Firebase{
//
//  private final String FCM_URL = "https://fcm.googleapis.com/v1/projects/modao-87283/registrations";
//  private final String SERVER_KEY = "AAAA497iBRQ:APA91bGt-ZV7HuzwWlEvVmrKTrg9LKzUVwifO0Uzy3RupKQc26wc6V6PxSDVcSu-ZgBiN-YKaJJ4zytNeEMATAYpkRM8psZL7Wyghx5i0aLMVkePB5NtbRkgQi0akrpL6_ZVGRlRMryO";
//  private final RestTemplate restTemplate = new RestTemplate();
//
//  public ResponseEntity<String> registerServer(String deviceToken, String packageName, String appVersion) {
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    headers.setBearerAuth(SERVER_KEY);
//
//    String jsonBody = "{ \"pushToken\": \"" + deviceToken + "\", "
//        + "\"platform\": \"android\", "
//        + "\"application\": \"" + packageName + "\", "
//        + "\"appVersion\": \"" + appVersion + "\" }";
//
//    return  restTemplate.postForEntity(FCM_URL, jsonBody, String.class);
//  }
//
//}

/*@Service
public class FirebaseImpl implements Firebase {

  private final String FCM_URL = "https://fcm.googleapis.com/v1/projects/modao-87283/registrations";
  private final String SERVER_KEY = "AAAA497iBRQ:APA91bGt-ZV7HuzwWlEvVmrKTrg9LKzUVwifO0Uzy3RupKQc26wc6V6PxSDVcSu-ZgBiN-YKaJJ4zytNeEMATAYpkRM8psZL7Wyghx5i0aLMVkePB5NtbRkgQi0akrpL6_ZVGRlRMryO";
  private final RestTemplate restTemplate = new RestTemplate();

  public ResponseEntity<String> registerServer(String deviceToken, String projectId) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(SERVER_KEY);

    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("token", deviceToken);

    HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

    return restTemplate.postForEntity(FCM_URL , requestEntity, String.class);
  }
}*/


