package com.rizz.learn.app.exception;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  // * Handle validasi error 400
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            FieldError::getDefaultMessage,
            (existing, replacement) -> existing));

    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Input is not valid, please check your data again.");
    problemDetail.setTitle("Validation Failed");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/validation-failed"));
    problemDetail.setProperty("errors", errors);

    return problemDetail;
  }

  // * Handle data tidak ditemukan (HTTP 404)
  @ExceptionHandler(NoSuchElementException.class)
  public ProblemDetail handleNotFoundException(NoSuchElementException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    problemDetail.setTitle("Resource Not Found");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/not-found"));

    return problemDetail;
  }

  // * Handle semua error tak terduga (HTTP 500)
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong on the server.");
    problemDetail.setTitle("Internal Server Error");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/server-error"));

    return problemDetail;
  }

}
