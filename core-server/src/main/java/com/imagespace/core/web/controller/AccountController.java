package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.service.AccountService;
import com.imagespace.core.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts/{accountId}")
    public Account findOne(@PathVariable String accountId) {
        return accountService
            .findAccountById(accountId)
            .orElseThrow(NotFoundException::new);
    }

}
