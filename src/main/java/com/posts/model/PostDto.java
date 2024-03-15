package com.posts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PostDto(
    @JsonProperty("id")
    Long id,
    @JsonProperty("title")
    String title,
    @JsonProperty("content")
    String content
) {}