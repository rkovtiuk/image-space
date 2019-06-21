package com.imagespace.core.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.core.domain.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("account_id")
    UUID accountId;

    @JsonProperty("source_id")
    UUID sourceId;

    @JsonProperty("likes")
    long like;

    public PostDto(Post post) {
        new PostDto(post.getId(), post.getAccount().getId(), post.getSource(), post.getLikes());
    }
}