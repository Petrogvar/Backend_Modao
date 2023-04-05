package com.SpringProject.core.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

  private final String type = "Bearer";
  private final Long id;
  private String accessToken;
  private String refreshToken;

}