package ru.otus.socialnetwork.service;

import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.socialnetwork.dto.UserResponse;
import ru.otus.socialnetwork.model.User;
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

  public List<UserResponse> searchUsers(String firstName, String lastName) {
    if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both first_name and last_name are required");
    }

    List<User> users = userRepository.searchByFirstNameAndSecondName(firstName, lastName);
    return users.stream()
        .map(user -> UserResponse.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .secondName(user.getSecondName())
            .gender(user.getGender())
            .birthdate(user.getBirthdate())
            .biography(user.getBiography())
            .city(user.getCity())
            .build())
        .collect(Collectors.toList());
  }
}