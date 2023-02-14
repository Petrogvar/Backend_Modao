package com.SpringProject.core.controllers.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user is not in the group")
public class UserNotGroupException extends RuntimeException {

}
