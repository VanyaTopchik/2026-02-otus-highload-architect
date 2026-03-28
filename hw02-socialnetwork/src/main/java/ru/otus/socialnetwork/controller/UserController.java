package ru.otus.socialnetwork.controller;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.socialnetwork.dto.RegisterRequest;
import ru.otus.socialnetwork.dto.UserResponse;
import ru.otus.socialnetwork.service.AuthService;
import ru.otus.socialnetwork.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final AuthService authService;
  private final UserService userService;

  @PostMapping("/register")
  public Map<String, String> register(@RequestBody RegisterRequest request) {
    UUID userId = authService.register(request);
    return Map.of("user_id", userId.toString());
  }

  @GetMapping("/get/{id}")
  public UserResponse getUser(@PathVariable String id) {
    return userService.getUserById(id);
  }
}