package com.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.posts.data.entities.PostEntity;
import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public record PostDto(
    @JsonProperty("id")
    Long id,
    @JsonProperty("title")
    String title,
    @JsonProperty("content")
    String content
) {
    public static @Nonnull PostDto fromEntity(PostEntity postEntity) {
        return PostDto.builder()
                      .id(postEntity.getId())
                      .title(postEntity.getTitle())
                      .content(postEntity.getContent())
                      .build();
    }

    public @Nonnull PostEntity toEntity() {
        return PostEntity.builder()
                         .title(title)
                         .content(content)
                         .build();
    }
}