package com.imagespace.apiserver.domain.service;

import com.imagespace.apiserver.domain.entity.Account;
import com.imagespace.apiserver.domain.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional<Account> findAccountById(String id) {
        return accountRepository.findOneById(id);
    }

}
