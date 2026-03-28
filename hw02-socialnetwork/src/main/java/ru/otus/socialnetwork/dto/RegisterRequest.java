package ru.otus.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterRequest(
    @JsonProperty("first_name")
    String firstName,
    @JsonProperty("second_name")
    String secondName,
    @JsonProperty("birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate birthdate,
    @JsonProperty("biography")
    String biography,
    @JsonProperty("gender")
    String gender,
    @JsonProperty("city")
    String city,
    @JsonProperty("password")
    String password
) {
}