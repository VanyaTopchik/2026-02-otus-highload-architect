package ru.otus.socialnetwork.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
    @JsonProperty("id")
    String id,
    @JsonProperty("password")
    String password
) {
}


