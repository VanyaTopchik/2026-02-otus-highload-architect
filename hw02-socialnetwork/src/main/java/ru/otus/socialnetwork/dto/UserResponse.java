package ru.otus.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponse(
    @JsonProperty("id")
    UUID id,
    @JsonProperty("first_name")
    String firstName,
    @JsonProperty("second_name")
    String secondName,
    @JsonProperty("gender")
    String gender,
    @JsonProperty("birthdate")
    LocalDate birthdate,
    @JsonProperty("biography")
    String biography,
    @JsonProperty("city")
    String city
) {
}
