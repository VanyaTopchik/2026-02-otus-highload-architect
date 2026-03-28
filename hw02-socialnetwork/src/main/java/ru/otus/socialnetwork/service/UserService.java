package ru.otus.socialnetwork.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.socialnetwork.model.User;
import ru.otus.socialnetwork.dto.UserResponse;
import ru.otus.socialnetwork.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserResponse getUserById(String id) {
    UUID userId;
    try {
      userId = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

    return UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .secondName(user.getSecondName())
        .gender(user.getGender())
        .birthdate(user.getBirthdate())
        .biography(user.getBiography())
        .city(user.getCity())
        .build();
  }
}