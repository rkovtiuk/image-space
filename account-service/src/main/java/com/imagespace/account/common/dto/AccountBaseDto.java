package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountBaseDto {

    @JsonProperty("id")
    UUID id;

    @JsonProperty("username")
    String username;

    @JsonProperty("avatar")
    UUID avatar;

}
