package com.imagespace.account.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagespace.account.domain.entity.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto extends AccountBaseDto {

    @JsonProperty("name")
    String name;

    @JsonProperty("info")
    String info;

    public AccountDto(Account account) {
        super(account.getId(), account.getUsername(), account.getAvatar());
        this.name = account.getName();
        this.info = account.getInfo();
    }

}
