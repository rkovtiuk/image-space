package com.imagespace.api.common.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;

@Getter
public record AccountDto(String username, String password, Collection<String> roles) implements Serializable {
}
