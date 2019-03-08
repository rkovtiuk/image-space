package com.imagespace.api.web;

import com.imagespace.api.domain.dto.AccountDto;
import com.imagespace.api.domain.service.AccountService;
import com.imagespace.api.web.exception.BadRequestException;
import com.imagespace.api.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping("/v1/account/signup")
    public void signUp(@RequestBody AccountDto body) {
        if (isEmpty(body.getId()) || isEmpty(body.getPassword()))
            throw new BadRequestException("Some fields are empty");

        if (accountService.accountExists(body.getId()))
            throw new BadRequestException("Account with same name already exists");

        accountService.createAccount(body.getId(), body.getPassword());
    }

    @GetMapping("/v1/account/{account}/exists")
    public HttpEntity<Boolean> accountExists(@PathVariable String account) {
        if (!accountService.accountExists(account))
            throw new NotFoundException();

        return new HttpEntity<>(true);
    }



}
