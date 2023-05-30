package com.SpringProject.core.controllers.Error.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "login already exists")
public class LoginException extends RuntimeException {
  public LoginException(String message) {
    super(message);
  }
}