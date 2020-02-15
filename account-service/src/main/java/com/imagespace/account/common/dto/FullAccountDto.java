package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.entity.AccountRole;
import com.imagespace.account.domain.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FullAccountDto extends AccountDto {

    @JsonProperty("password")
    String password;

    @JsonProperty("roles")
    Collection<String> roles;

    public FullAccountDto(Account account) {
        super(account);
        this.password = account.getPassword();
        Optional.ofNullable(account.getAccountRoles())
            .map(roles -> roles.stream().map(AccountRole::getRole).map(Role::getName).collect(toList()))
            .ifPresent(this::setRoles);
    }
}
