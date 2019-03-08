package com.imagespace.api.web.rest;

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
public class SignUpController {

    private final AccountService accountService;

    @PostMapping("/v1/signup")
    public void signUp(@RequestBody AccountDto body) {
        if (isEmpty(body.getId()) || isEmpty(body.getPassword()))
            throw new BadRequestException("Some fields are empty");

        if (accountService.accountExists(body.getId()))
            throw new BadRequestException("Account with same name already exists");

        accountService.createAccount(body.getId(), body.getPassword());
    }

    @GetMapping("/v1/signup/{account}")
    public HttpEntity<Boolean> accountExists(@PathVariable String account) {
        if (!accountService.accountExists(account))
            throw new NotFoundException();

        return new HttpEntity<>(true);
    }



}
