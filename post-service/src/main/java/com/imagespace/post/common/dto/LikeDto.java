package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.post.domain.entity.Like;
import lombok.Getter;

import java.util.UUID;

@Getter
public record LikeDto(@JsonProperty("id") UUID id,
                      @JsonProperty("account_id") UUID accountId,
                      @JsonProperty("post_id") UUID postId) {

    public LikeDto(Like like) {
        this(like.getId(), like.getAccountId(), like.getPost().getId());
    }

}
