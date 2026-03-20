package ru.otus.socialnetwork.service;

import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.socialnetwork.model.User;
import ru.otus.socialnetwork.dto.LoginRequest;
import ru.otus.socialnetwork.dto.RegisterRequest;
import ru.otus.socialnetwork.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public String login(LoginRequest request) {
    if (request.id() == null || request.password() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id and password are required");
    }

    UUID userId;
    try {
      userId = UUID.fromString(request.id());
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
    }

    UUID token = UUID.randomUUID();
    userRepository.updateToken(userId, token);

    return token.toString();
  }

  public UUID register(RegisterRequest request) {
    if (request.firstName() == null || request.firstName().isEmpty() ||
        request.secondName() == null || request.secondName().isEmpty() ||
        request.birthdate() == null ||
        request.password() == null || request.password().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required fields");
    }

    if (request.birthdate().isAfter(LocalDate.now())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Birthdate cannot be in the future");
    }

    User user = new User();
    user.setFirstName(request.firstName());
    user.setSecondName(request.secondName());
    user.setBirthdate(request.birthdate());
    user.setBiography(request.biography());
    user.setGender(request.gender());
    user.setCity(request.city());
    user.setPasswordHash(passwordEncoder.encode(request.password()));

    User saved = userRepository.save(user);
    return saved.getId();
  }
}