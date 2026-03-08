package com.rizz.learn.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/api/hello")
  public String helloRizz() {
    return "Welcome to Spring Boot 4.0, Rizz! Let's build awesome APIs!";
  }
}
