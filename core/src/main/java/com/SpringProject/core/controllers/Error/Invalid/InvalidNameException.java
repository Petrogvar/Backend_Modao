package com.SpringProject.core.controllers.Error.Invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidNameException extends RuntimeException {
  public InvalidNameException(String message) {
    super(message);
  }
}