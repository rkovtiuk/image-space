package com.imagespace.account.domain.service;

import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    @Cacheable(value = "accounts", key = "#id")
    public Optional<Account> findAccountById(UUID id) {
        log.debug("Get account by id '{}'.", id);
        return accountRepository.findOneById(id);
    }

    @Cacheable(value = "usernames", key = "#username")
    public Optional<Account> findAccountByUsername(String username) {
        log.debug("Get account by username '{}'.", username);
        return accountRepository.findOneByUsername(username);
    }

    public boolean exists(UUID accountId) {
        log.debug("Check if account exists for id '{}'.", accountId);
        return accountRepository.existsAccountById(accountId);
    }

    @Transactional
    @CachePut(value = "accounts", key = "#id")
    public Account createAccount(UUID id, String password) {
        log.debug("Creating account with id '{}'.", id);
        return accountRepository.save(
            new Account()
                .setId(id)
                .setPassword(password));
    }

}
