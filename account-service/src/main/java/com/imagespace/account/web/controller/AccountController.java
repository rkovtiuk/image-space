package com.imagespace.account.web.controller;

import com.imagespace.account.common.dto.AccountDto;
import com.imagespace.account.common.dto.CredentialsDto;
import com.imagespace.account.common.dto.FullAccountDto;
import com.imagespace.account.common.exception.HttpExceptionBuilder;
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

    @GetMapping("/{username}/full-info")
    public FullAccountDto getAccountWithCredentialsAndRoles(@PathVariable("username") String username) {
        return accountService
            .findAccountFullInfo(username)
            .orElseThrow(() -> HttpExceptionBuilder.notFound(String.format("Can't find account by username '%s'", username)));
    }

    @GetMapping
    public AccountDto getAccount(@RequestParam(required = false) UUID id,
                                 @RequestParam(required = false) String username) {
        return accountService
            .findAccount(id, username)
            .map(AccountDto::new)
            .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account"));
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody CredentialsDto credentials) {
        var account = accountService.createAccount(credentials.getUsername(), credentials.getPassword());
        return new AccountDto(account);
    }

    @PutMapping("/{accountId}")
    public AccountDto updateAccount(@PathVariable UUID id, @RequestBody AccountDto request) {
        return accountService
            .updateAccount(id, request.getUsername(), request.getAvatar(), request.getInfo())
            .map(AccountDto::new)
            .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account"));
    }

    @GetMapping("/exists")
    public ResponseEntity<?> accountExists(@RequestParam(required = false) UUID id,
                                           @RequestParam(required = false) String username) {
        return accountService.exists(id, username)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

}
