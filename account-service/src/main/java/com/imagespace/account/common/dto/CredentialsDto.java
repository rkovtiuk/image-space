package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public record CredentialsDto(@JsonProperty("username") String username,
                             @JsonProperty("password]") String password) {
}
