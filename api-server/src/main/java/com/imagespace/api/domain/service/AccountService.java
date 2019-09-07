package com.imagespace.api.domain.service;

import com.imagespace.api.domain.client.CoreClient;
import com.imagespace.api.dto.AccountDto;
import com.imagespace.api.web.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    CoreClient coreClient;
    BCryptPasswordEncoder bCryptEncoder;

    public void createAccount(String id, String password) {
        var pwd = bCryptEncoder.encode(password);
        coreClient
            .createAccount(new AccountDto().setId(id).setPassword(pwd))
            .orElseThrow(() -> new BadRequestException("Can't create account"));
    }

    public boolean accountExists(String id) {
        return coreClient.accountExists(id);
    }

}
