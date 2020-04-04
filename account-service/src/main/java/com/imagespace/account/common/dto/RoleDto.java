package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.account.domain.entity.Role;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record RoleDto(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("created_at") LocalDateTime createdAt,
                      @JsonProperty("updated_at") LocalDateTime updatedAt) {

    public RoleDto(Role role) {
        this(role.getId(), role.getName(), role.getCreatedAt(), role.getUpdatedAt());
    }

}
