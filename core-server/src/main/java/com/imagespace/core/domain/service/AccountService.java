package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Subscribe;
import com.imagespace.core.domain.repositories.AccountRepository;
import com.imagespace.core.domain.repositories.SubscribeRepository;
import com.imagespace.core.web.dto.ExistsDto;
import com.imagespace.core.web.dto.SubscribeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final SubscribeRepository subscribeRepository;

    public Optional<Account> findAccountById(UUID id) {
        return accountRepository.findOneById(id);
    }

    public ExistsDto exists(UUID accountId) {
        return new ExistsDto(accountRepository.existsAccountById(accountId));
    }

    @Transactional
    public Account createAccount(UUID id, String password) {
        return accountRepository.save(
            new Account()
                .setId(id)
                .setPassword(password));
    }

    public List<Account> getSubscribes(UUID accountId, Pageable pageable) {
        return subscribeRepository
                .findAllBySubscribingId(accountId, pageable)
                .map(Subscribe::getSubscriber)
                .getContent();
    }

    @Transactional
    public void subscribe(UUID subscribingId, SubscribeDto dto) {
        subscribeRepository
                .findFirstBySubscriberIdAndSubscribingId(dto.getSubscriber(), subscribingId)
                .ifPresentOrElse(
                        subscribe -> log.error("Can't subscribe {} to {}. Subscribe already exists", dto.getSubscriber(), subscribingId),
                        () -> subscribeRepository.save(new Subscribe(dto.getSubscriber(), subscribingId)));
    }

    @Transactional
    public void unsubscribe(UUID subscribingId, SubscribeDto dto) {
        subscribeRepository
                .findFirstBySubscriberIdAndSubscribingId(dto.getSubscriber(), subscribingId)
                .ifPresent(subscribeRepository::delete);
    }
}
