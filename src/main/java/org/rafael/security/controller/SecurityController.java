package org.rafael.security.controller;

import org.rafael.security.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
  @Autowired
  SimpleService simpleService;
  @GetMapping("/api/public")
  public ResponseEntity<String> get() {
    return ResponseEntity.ok(simpleService.getMessage());
  }
  @PostMapping("/api/private")
  public ResponseEntity<String> post(@RequestBody Data data) {
    System.out.println("Message: " + data.message);
    return ResponseEntity.ok("Hello World");
  }
  private static class Data {
    String message;
  }
}
