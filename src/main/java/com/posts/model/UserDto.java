package com.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posts.data.entities.UserEntity;
import jakarta.annotation.Nonnull;
import lombok.Builder;


@Builder
public record UserDto(

    @JsonProperty("id") Long id,
    @JsonProperty("username") String username,
    @JsonProperty("password") String password) {

    public static @Nonnull UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder()
                      .id(userEntity.getId())
                      .username(userEntity.getUsername())
                      .password(userEntity.getPassword())
                      .build();
    }

    public @Nonnull UserEntity toEntity() {
        return UserEntity.builder()
            .username(username)
            .password(password)
            .build();
    }
}