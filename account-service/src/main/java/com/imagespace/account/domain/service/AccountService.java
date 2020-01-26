package com.imagespace.account.domain.service;

import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.entity.Subscribe;
import com.imagespace.account.domain.repositories.AccountRepository;
import com.imagespace.account.domain.repositories.SubscribeRepository;
import com.imagespace.account.common.dto.ExistsDto;
import com.imagespace.account.common.dto.SubscribeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountService {

    private final AccountRepository accountRepository;
    private final SubscribeRepository subscribeRepository;

    @Cacheable(value = "accounts", key = "#id")
    public Optional<Account> findAccountById(UUID id) {
        return accountRepository.findOneById(id);
    }

    public boolean exists(UUID accountId) {
        return accountRepository.existsAccountById(accountId);
    }

    @Transactional
    @CachePut(value = "accounts", key = "#id")
    public Account createAccount(UUID id, String password) {
        log.debug("Creating account with id {}.", id);
        return accountRepository.save(
            new Account()
                .setId(id)
                .setPassword(password));
    }

    @Cacheable(value = "accounts", key = "#id")
    public List<Account> getSubscribes(UUID accountId, Pageable pageable) {
        log.debug("Search subscribes by account id {} and parameters {}.", accountId, pageable);
        return subscribeRepository
                .findAllBySubscribingId(accountId, pageable)
                .map(Subscribe::getSubscriber)
                .getContent();
    }

    @Transactional
    public void subscribe(UUID subscribingId, SubscribeDto dto) {
        log.debug("Creating subscribe with id {} and data {}.", subscribingId, dto);
        subscribeRepository
                .findFirstBySubscriberIdAndSubscribingId(dto.getSubscriber(), subscribingId)
                .ifPresentOrElse(
                        subscribe -> log.error("Can't subscribe {} to {}. Subscribe already exists", dto.getSubscriber(), subscribingId),
                        () -> subscribeRepository.save(new Subscribe(dto.getSubscriber(), subscribingId)));
    }

    @Transactional
    public void unsubscribe(UUID subscribingId, SubscribeDto dto) {
        log.debug("Removing subscribe with id {} and data {}.", subscribingId, dto);
        subscribeRepository
                .findFirstBySubscriberIdAndSubscribingId(dto.getSubscriber(), subscribingId)
                .ifPresent(subscribeRepository::delete);
    }
}
