package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.repositories.AccountRepository;
import com.imagespace.core.web.dto.ExistsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional<Account> findAccountById(UUID id) {
        return accountRepository.findOneById(id);
    }

    public ExistsDto exists(UUID accountId) {
        return new ExistsDto(accountRepository.existsAccountById(accountId));
    }

    public Account createAccount(UUID id, String password) {
        return accountRepository.save(
            new Account()
                .setId(id)
                .setPassword(password));
    }
}
