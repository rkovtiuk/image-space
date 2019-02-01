package com.imagespace.apiserver.domain.service;

import com.imagespace.apiserver.domain.entity.Account;
import com.imagespace.apiserver.domain.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

}
