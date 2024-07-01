package rj.training.rest.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rj.training.rest.model.Movie;

@ControllerAdvice
public class MyExceptionHandler {

  @ExceptionHandler(DuplicateMovieException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(DuplicateMovieException ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.toString());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }
  
  //we can handle multiple exceptions by one handler
  @ExceptionHandler({InvalidMovieException.class, DataIntegrityViolationException.class})
 //have the base exception of both exceptions as parameter
  public ResponseEntity<ErrorResponse> handleInvalidMovie(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.toString());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
  
  //we can have separate exception handler if we want to have different type of handling
 /* @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleInvalidMovieDataIntegrity(DataIntegrityViolationException ex) {
  //ex.tostring will return name and message of the exception
	  ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.toString());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }*/
  
}

