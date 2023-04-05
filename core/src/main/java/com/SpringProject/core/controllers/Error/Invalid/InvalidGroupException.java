package com.SpringProject.core.controllers.Error.Invalid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidGroupException extends RuntimeException {
  public InvalidGroupException(String message) {
    super(message);
  }

  public InvalidGroupException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidGroupException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  private HttpStatus httpStatus;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
