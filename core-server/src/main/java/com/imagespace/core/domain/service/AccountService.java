package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.repositories.AccountRepository;
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

    public boolean exists(String accountId) {
        return accountRepository.existsAccountById(accountId);
    }

    public Account createAccount(String id, String password) {
        return accountRepository.save(
            new Account()
                .setId(id)
                .setPassword(password));
    }
}
