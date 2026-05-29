package com.rizz.learn.app.exception;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

  // * Handle illegal argument (HTTP 400) — misal email sudah terdaftar
  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    problemDetail.setTitle("Bad Request");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/bad-request"));

    return problemDetail;
  }

  // * Handle akses ditolak (HTTP 403) — user tidak punya role yang cukup
  @ExceptionHandler(AccessDeniedException.class)
  public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.FORBIDDEN, "You don't have permission to access this resource.");
    problemDetail.setTitle("Access Denied");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/access-denied"));

    return problemDetail;
  }

  // * Handle belum login (HTTP 401)
  @ExceptionHandler(AuthenticationException.class)
  public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Authentication is required to access this resource.");
    problemDetail.setTitle("Unauthorized");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/unauthorized"));

    return problemDetail;
  }

  // * Handle semua error tak terduga (HTTP 500)
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception ex) {
    log.error("Unexpected error occurred: {}", ex.getMessage(), ex);

    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Something is wrong on the server.");
    problemDetail.setTitle("Internal Server Error");
    problemDetail.setType(URI.create("https://rizz-app.com/errors/server-error"));

    return problemDetail;
  }
}
