package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("subscriber_id")
    UUID subscriber;

    @JsonProperty("subscribing_id")
    UUID subscribing;

}
