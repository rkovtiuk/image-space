package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.service.AccountService;
import com.imagespace.core.web.dto.ExistsDto;
import com.imagespace.core.web.dto.SubscribeDto;
import com.imagespace.core.web.exception.HttpExceptionBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account with id " + accountId.toString()));
    }

    @GetMapping("{accountId}/exists")
    public ExistsDto exists(@PathVariable UUID accountId) {
        return accountService.exists(accountId);
    }

    @GetMapping("/{accountId}/subscribes")
    public List<Account> subscribes(@PathVariable UUID accountId, Pageable pageable) {
        return accountService.getSubscribes(accountId, pageable);
    }

    @PostMapping("/{accountId}/subscribes")
    public void subscribe(@PathVariable UUID accountId, @RequestBody SubscribeDto dto) {
        accountService.subscribe(accountId, dto);
    }

    @DeleteMapping("/{accountId}/subscribes")
    public void unsubscribe(@PathVariable UUID accountId, @RequestBody SubscribeDto dto) {
        accountService.unsubscribe(accountId, dto);
    }

}
