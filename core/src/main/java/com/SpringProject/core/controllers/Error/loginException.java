package com.SpringProject.core.controllers.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "login already exists")
public class loginException extends RuntimeException {
}