package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public record SourceDto(@JsonProperty("id") UUID id,
                        @JsonProperty("source") byte[] source){
}
