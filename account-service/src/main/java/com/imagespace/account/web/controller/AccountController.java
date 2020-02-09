package com.imagespace.account.web.controller;

import com.imagespace.account.common.exception.HttpExceptionBuilder;
import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;


    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account.getId(), account.getPassword());
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable UUID accountId) {
        return accountService
                .findAccountById(accountId)
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account with id " + accountId.toString()));
    }

    @GetMapping("/usernames/{username}")
    public Account getAccountByUsername(@PathVariable String username) {
        return accountService
            .findAccountByUsername(username)
            .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account with username " + username));
    }

    @GetMapping("/{accountId}/exists")
    public ResponseEntity<?> accountExists(@PathVariable UUID accountId) {
        return accountService.exists(accountId)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

}
