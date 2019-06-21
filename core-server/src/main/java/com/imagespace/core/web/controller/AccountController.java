package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.service.AccountService;
import com.imagespace.core.web.dto.ExistsDto;
import com.imagespace.core.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;


    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account.getId(), account.getPassword());
    }

    @GetMapping("/{accountId}")
    public Account findOne(@PathVariable UUID accountId) {
        return accountService
            .findAccountById(accountId)
            .orElseThrow(NotFoundException::new);
    }

    @GetMapping("{accountId}/exists")
    public ExistsDto exists(@PathVariable UUID accountId) {
        return accountService.exists(accountId);
    }

}
