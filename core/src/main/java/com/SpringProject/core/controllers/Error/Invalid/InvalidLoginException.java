package com.SpringProject.core.controllers.Error.Invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidLoginException extends RuntimeException {
  public InvalidLoginException(String message) {
    super(message);
  }
}