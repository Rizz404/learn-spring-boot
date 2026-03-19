package com.rizz.learn.app.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

  public String generateGreeting(String name) {
    return "Welcome to Spring Boot 4.0, " + name + "! This is from the Service layer!";
  }
}
