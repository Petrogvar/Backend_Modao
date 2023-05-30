package com.SpringProject.core.controllers.Error.Invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException(String message) {
    super(message, new Throwable(message));
  }
}