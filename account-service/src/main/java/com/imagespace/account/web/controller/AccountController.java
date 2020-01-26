package com.imagespace.account.web.controller;

import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.service.AccountService;
import com.imagespace.account.common.dto.ExistsDto;
import com.imagespace.account.common.dto.SubscribeDto;
import com.imagespace.account.common.exception.HttpExceptionBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Account findOne(@PathVariable UUID accountId) {
        return accountService
                .findAccountById(accountId)
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find account with id " + accountId.toString()));
    }

    @GetMapping("{accountId}/exists")
    public ResponseEntity exists(@PathVariable UUID accountId) {
        return accountService.exists(accountId)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
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
