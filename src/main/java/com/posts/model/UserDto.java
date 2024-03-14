package com.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posts.data.entities.UserEntity;
import com.posts.data.entities.UserRole;
import jakarta.annotation.Nonnull;
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
) {

    public static @Nonnull UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder()
                      .id(userEntity.getId())
                      .username(userEntity.getUsername())
                      .password(userEntity.getPassword())
                      .role(userEntity.getRole())
                      .first_name(userEntity.getFirst_name())
                      .last_name(userEntity.getLast_name())
                      .enabled(userEntity.isEnabled())
                      .created_at(userEntity.getCreated_at())
                      .updated_at(userEntity.getUpdated_at())
                      .build();
    }

    public @Nonnull UserEntity toEntity() {
        return UserEntity.builder()
            .username(username)
            .password(password)
            .role(role)
            .first_name(first_name)
            .last_name(last_name)
            .enabled(enabled)
            .created_at(created_at)
            .updated_at(updated_at)
            .build();
    }
}