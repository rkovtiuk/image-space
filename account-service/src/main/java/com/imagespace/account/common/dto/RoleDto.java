package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.account.domain.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    LocalDateTime updatedAt;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
    }

}
