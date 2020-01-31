package com.imagespace.api.common.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SourceDto {

    @JsonProperty UUID id;

    @JsonProperty byte[] source;

}
