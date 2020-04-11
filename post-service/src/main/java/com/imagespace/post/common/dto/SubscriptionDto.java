package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public record SubscriptionDto(@JsonProperty("id") UUID subscriptionId,
                              @JsonProperty("follower_id") UUID follower,
                              @JsonProperty("following_id") UUID following) {
}
