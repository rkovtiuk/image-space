package com.imagespace.account.domain.service;

import com.imagespace.account.common.dto.AccountFullDto;
import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

import static java.util.function.Predicate.not;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    @Transactional
    @CachePut(value = "usernames", key = "#username")
    public Account createAccount(String username, String password) {
        log.debug("Creating account with username '{}'.", username);
        var account = new Account().setId(UUID.randomUUID()).setName(username).setPassword(password);
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "usernames", key = "#accountId + #username")
    public Optional<Account> findAccount(UUID accountId, String username) {
        log.debug("Find account by id '{}' and username '{}'.", accountId, username);
        return accountRepository.findOne(exampleOf(accountId, username));
    }

    @Transactional(readOnly = true)
    public Optional<AccountFullDto> findAccountFullInfo(String username) {
        return accountRepository.findOneByUsername(username).map(AccountFullDto::new);
    }

    @Transactional
    public Optional<Account> updateAccount(UUID id, String username, UUID avatar, String info) {
        log.debug("Update account '{}' with param: username:'{}' avatar:'{}' info:'{}'.", id, username, avatar, info);
        return accountRepository.findOne(Example.of(new Account().setUsername(username)))
            .map(account -> {
                Optional.ofNullable(info).filter(not(StringUtils::isEmpty)).ifPresent(account::setInfo);
                Optional.ofNullable(avatar).filter(not(StringUtils::isEmpty)).ifPresent(account::setAvatar);
                Optional.ofNullable(username).filter(not(StringUtils::isEmpty)).ifPresent(account::setUsername);
                return account;
            }).map(accountRepository::save);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "exists", key = "#accountId + #username")
    public boolean exists(UUID accountId, String username) {
        log.debug("Check if account exists for id '{}'.", accountId);
        return accountRepository.exists(exampleOf(accountId, username));
    }

    private Example<Account> exampleOf(UUID accountId, String username) {
        return Example.of(new Account().setId(accountId).setUsername(username));
    }
}
