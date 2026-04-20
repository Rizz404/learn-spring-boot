package com.rizz.learn.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizz.learn.app.config.AppProperties;
import com.rizz.learn.app.services.GreetingService;

/*
@Component: Tanda paling umum (General purpose).
@Service: Khusus untuk class yang isinya business logic (Logika utama aplikasi).
@Repository: Khusus untuk class yang interaksi sama Database.
@Controller / @RestController: Khusus untuk nerima request dari luar (HTTP Request).
*/

@RestController
public class HelloController {

  private final GreetingService greetingService;
  private final AppProperties appProperties;

  public HelloController(GreetingService greetingService, AppProperties appProperties) {
    this.greetingService = greetingService;
    this.appProperties = appProperties;
  }

  // * Sesi 1.1
  // @GetMapping("/api/hello")
  // public String helloRizz() {
  // return "Welcome to Spring Boot 4.0, Rizz! Let's build awesome APIs!";
  // }

  // * Revactor
  @GetMapping("/api/hello")
  public String helloRizz() {
    String greeting = greetingService.generateGreeting("Rizz");
    String appInfo = " | App: " + appProperties.name() + " v" + appProperties.version();

    return greeting + appInfo;
  }

}
