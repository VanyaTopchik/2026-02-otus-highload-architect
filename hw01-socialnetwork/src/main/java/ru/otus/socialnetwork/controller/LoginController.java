package ru.otus.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.socialnetwork.dto.LoginRequest;
import ru.otus.socialnetwork.dto.LoginResponse;
import ru.otus.socialnetwork.service.AuthService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final AuthService authService;

  @PostMapping
  public LoginResponse login(@RequestBody LoginRequest request) {
    String token = authService.login(request);
    return LoginResponse.builder().token(token).build();
  }
}