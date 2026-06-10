package com.rizz.learn.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rizz.learn.app.dto.AuthRequest;
import com.rizz.learn.app.dto.AuthResponse;
import com.rizz.learn.app.dto.RegisterRequest;
import com.rizz.learn.app.entity.User;
import com.rizz.learn.app.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
    return authService.register(request);
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody AuthRequest request) {
    return authService.login(request);
  }

  @GetMapping("/me")
  public AuthResponse getCurrentUser(
      @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails principal) {
    return authService.getCurrentUser(principal.getUsername());
  }

}
