package com.imagespace.api.domain.service;

import com.imagespace.api.common.dto.AccountDto;
import com.imagespace.api.common.exception.BadRequestException;
import com.imagespace.api.domain.client.AccountClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountClient accountClient;
    BCryptPasswordEncoder bCryptEncoder;

    public void createAccount(String id, String password) {
        var pwd = bCryptEncoder.encode(password);
        accountClient
            .createAccount(new AccountDto().setId(id).setPassword(pwd))
            .orElseThrow(() -> new BadRequestException("Can't create account"));
    }

    public boolean accountExists(String id) {
        return accountClient.accountExists(id);
    }

}
