package com.imagespace.source.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public record EventDto(@JsonProperty("event_name") String eventName,
                       @JsonProperty("payload") SourceDto body)
        implements Serializable {
}
