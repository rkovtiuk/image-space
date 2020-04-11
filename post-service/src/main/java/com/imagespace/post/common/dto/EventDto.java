package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record EventDto<T>(@JsonProperty("event_name") String eventName,
                          @JsonProperty("payload") T payload) {
}
