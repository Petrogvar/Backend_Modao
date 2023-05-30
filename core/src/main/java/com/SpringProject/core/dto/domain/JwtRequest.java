package com.SpringProject.core.dto.domain;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class JwtRequest {

  private String login;
  private String password;

  private String deviceToken;
  private String packageName;
  private String appVersion;

}