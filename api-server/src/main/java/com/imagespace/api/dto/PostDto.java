package com.imagespace.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("account_id")
    UUID accountId;

    @JsonProperty("source_id")
    UUID sourceId;

    @JsonProperty("likes")
    long likes;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    LocalDateTime updatedAt;

    public PostDto(UUID id, UUID accountId, UUID sourceId) {
        this.id = id;
        this.accountId = accountId;
        this.sourceId = sourceId;
    }

}

