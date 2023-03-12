package com.SpringProject.core.controllers.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "BAD_REQUEST")
public class AuthException extends RuntimeException{

}
