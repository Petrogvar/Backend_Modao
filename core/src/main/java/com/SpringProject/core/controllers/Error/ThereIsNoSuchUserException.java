package com.SpringProject.core.controllers.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "invalid password or login")
public class ThereIsNoSuchUserException extends RuntimeException {
}
