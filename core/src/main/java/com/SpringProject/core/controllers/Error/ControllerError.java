package com.SpringProject.core.controllers.Error;


import com.SpringProject.core.controllers.Error.Exception.AuthException;
import com.SpringProject.core.controllers.Error.Exception.BadRequestException;
import com.SpringProject.core.controllers.Error.Exception.LoginException;
import com.SpringProject.core.controllers.Error.Exception.NotFoundException;
import com.SpringProject.core.controllers.Error.Exception.NotRightException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidEventException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidGroupException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidLoginException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidNameException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidPasswordException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerError extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidPasswordException.class)
  protected ResponseEntity<AwesomeException> handlePasswordException(InvalidPasswordException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InvalidNameException.class)
  protected ResponseEntity<AwesomeException> handleNameException(InvalidNameException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InvalidLoginException.class)
  protected ResponseEntity<AwesomeException> handleLoginException(InvalidLoginException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InvalidGroupException.class)
  protected ResponseEntity<AwesomeException> handleGroupException(InvalidGroupException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InvalidEventException.class)
  protected ResponseEntity<AwesomeException> handleEventException(InvalidEventException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }



  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException(NotFoundException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(LoginException.class)
  protected ResponseEntity<AwesomeException> loginException(LoginException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotRightException.class)
  protected ResponseEntity<AwesomeException> NotRightException() {
    return new ResponseEntity<>(new AwesomeException("not right"),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<AwesomeException> BadRequestException(BadRequestException ex) {
    return new ResponseEntity<>(new AwesomeException(ex.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthException.class)
  protected ResponseEntity<AwesomeException> AuthException() {
    return new ResponseEntity<>(new AwesomeException("the password or login is not correct"),
        HttpStatus.BAD_REQUEST);
  }
  @Data
  @AllArgsConstructor
  private static class AwesomeException {

    private String message;
  }
}
