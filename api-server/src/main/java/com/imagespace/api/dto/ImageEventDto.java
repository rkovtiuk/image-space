package com.imagespace.api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
public class ImageEventDto {

    @JsonProperty String id;

    @JsonProperty byte[] source;

}
