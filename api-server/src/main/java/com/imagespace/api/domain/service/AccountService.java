package com.imagespace.api.domain.service;

import com.imagespace.api.domain.client.CoreClient;
import com.imagespace.api.domain.dto.AccountDto;
import com.imagespace.api.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CoreClient coreClient;
    private final BCryptPasswordEncoder bCryptEncoder;

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
