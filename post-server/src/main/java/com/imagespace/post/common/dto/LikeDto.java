package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.post.domain.entity.Like;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LikeDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("account_id")
    UUID accountId;

    @JsonProperty("post_id")
    UUID postId;

    public LikeDto(Like like) {
        this.id = like.getId();
        this.accountId = like.getAccountId();
        this.postId = like.getPost().getId();
    }

}
