package com.SpringProject.core.controllers.Error.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "not right")
public class NotRightException extends RuntimeException{

}
