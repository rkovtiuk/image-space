package com.imagespace.account.domain.service;

import com.imagespace.account.domain.entity.Account;
import com.imagespace.account.domain.entity.Subscription;
import com.imagespace.account.domain.repositories.SubscriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionService {

    SubscriptionRepository subscriptionRepository;

    public Page<Subscription> getSubscriptions(UUID followerId, Pageable page) {
        return subscriptionRepository.findAllByFollower_Id(followerId, page);
    }

    public Page<Account> getSubscriptionsAccounts(UUID followerId, Pageable pageable) {
        log.debug("Search subscribes by account '{}' and parameters {}.", followerId, pageable);
        return subscriptionRepository
                .findAllByFollower_Id(followerId, pageable)
                .map(Subscription::getFollowing);
    }

    @Transactional
    public Subscription follow(UUID followerId, UUID followingId) {
        log.debug("Creating subscription for account '{}' on '{}'.", followerId, followingId);
        return subscriptionRepository
            .findFirstByFollower_IdAndFollowing_Id(followerId, followingId)
            .orElseGet(() -> subscriptionRepository.save(new Subscription(followerId, followingId)));
    }

    @Transactional
    public void unfollow(UUID followerId, UUID followingId) {
        log.debug("Removing subscription for account '{}' on '{}'.", followerId, followingId);
        subscriptionRepository
            .findFirstByFollower_IdAndFollowing_Id(followerId, followingId)
            .ifPresent(subscriptionRepository::delete);
    }

}
