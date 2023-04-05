package com.speakingclock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author MADHU H
 *
 */

@ControllerAdvice
public class InputTimeNotfoundExceptionController {
	@ExceptionHandler(value = InputTimeNotfoundException.class)
	   public ResponseEntity<Object> exception(InputTimeNotfoundException exception) {
	      return new ResponseEntity<>("Input Time parameter not found", HttpStatus.NOT_FOUND);
	   }
}
