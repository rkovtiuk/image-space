package com.imagespace.source.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record SourceDto(@JsonProperty String id,
                        @JsonProperty byte[] source) {

}

