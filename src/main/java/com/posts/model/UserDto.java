package com.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posts.data.entities.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(

    @JsonProperty("id") Long id,
    @JsonProperty("username") String username,
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY) String password,
    @JsonProperty("role") UserRole role,
    @JsonProperty("first_name") String first_name,
    @JsonProperty("last_name") String last_name,
    @JsonProperty("enabled") boolean enabled,
    @JsonProperty("created_at") LocalDateTime created_at,
    @JsonProperty("updated_at") LocalDateTime updated_at
) { }