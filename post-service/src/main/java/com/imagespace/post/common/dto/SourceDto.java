package com.imagespace.post.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("source")
    byte[] source;

}
