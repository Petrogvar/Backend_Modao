package com.SpringProject.core.controllers.Error;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerError extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ThereIsNoSuchUserException.class)
  protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
    return new ResponseEntity<>(new AwesomeException("not found"), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(LoginException.class)
  protected ResponseEntity<AwesomeException> loginException() {
    return new ResponseEntity<>(new AwesomeException("login already exists"),
        HttpStatus.BAD_REQUEST);
  }

  @Data
  @AllArgsConstructor
  private static class AwesomeException {

    private String message;
  }
}
