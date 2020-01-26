package com.imagespace.api.web.rest;

import com.imagespace.api.domain.service.AccountService;
import com.imagespace.api.dto.AccountDto;
import com.imagespace.api.web.exception.BadRequestException;
import com.imagespace.api.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/signup")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SignUpController {

    private final AccountService accountService;

    @PostMapping
    public void signUp(@RequestBody AccountDto body) {
        if (isEmpty(body.getId()) || isEmpty(body.getPassword()))
            throw new BadRequestException("Some fields are empty");

        if (accountService.accountExists(body.getId()))
            throw new BadRequestException("Account with same name already exists");

        accountService.createAccount(body.getId(), body.getPassword());
    }

    @GetMapping("/{account}")
    public HttpEntity<Boolean> accountExists(@PathVariable String account) {
        if (!accountService.accountExists(account))
            throw new NotFoundException();

        return new HttpEntity<>(true);
    }



}
