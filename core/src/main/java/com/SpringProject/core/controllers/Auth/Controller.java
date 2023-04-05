package com.SpringProject.core.controllers.Auth;

import com.SpringProject.core.Services.Auth.AuthService;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class Controller {

  private final AuthService authService;

  @GetMapping("hello/user")
  public ResponseEntity<String> helloUser() {
    final JwtAuthentication authInfo = authService.getAuthInfo();
    return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
  }

  @GetMapping("hello/admin")
  public ResponseEntity<String> helloAdmin() {
    final JwtAuthentication authInfo = authService.getAuthInfo();
    return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
  }

}