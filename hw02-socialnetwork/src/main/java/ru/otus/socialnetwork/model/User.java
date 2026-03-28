package ru.otus.socialnetwork.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private UUID id;
  private String firstName;
  private String secondName;
  private String gender;
  private LocalDate birthdate;
  private String biography;
  private String city;
  private String passwordHash;
  private UUID token;
}
