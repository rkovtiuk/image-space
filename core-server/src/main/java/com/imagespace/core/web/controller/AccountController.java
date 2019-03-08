package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.service.AccountService;
import com.imagespace.core.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account.getId(), account.getPassword());
    }

    @GetMapping("/accounts/{accountId}")
    public Account findOne(@PathVariable String accountId) {
        return accountService
            .findAccountById(accountId)
            .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/accounts/{accountId}/exists")
    public boolean exists(@PathVariable String accountId) {
        return accountService.exists(accountId);
    }

}
