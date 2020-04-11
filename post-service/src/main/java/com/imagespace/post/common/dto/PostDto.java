package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.post.domain.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public record PostDto(@JsonProperty("id") UUID id,
                     @JsonProperty("account_id") UUID accountId,
                     @JsonProperty("source_id") UUID sourceId,
                     @JsonProperty("likes") long likes,
                     @JsonProperty("created_at") LocalDateTime createdAt,
                     @JsonProperty("updated_at") LocalDateTime updatedAt) {

    public PostDto(Post post) {
        this(post.getId(), post.getAccountId(), post.getSource(), post.getLikes(), post.getCreatedAt(), post.getUpdatedAt());
    }

}
