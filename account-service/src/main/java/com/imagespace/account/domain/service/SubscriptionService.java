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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.imagespace.account.common.util.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionService {

    SubscriptionRepository subscriptionRepository;

    public Page<Subscription> getSubscriptions(UUID followerId, Pageable page) {
        var sort = Sort.by("priority").descending().and(Sort.by("createdAt").ascending());
        var pageRequest = PageRequest.of(page.getPageNumber(), Math.min(page.getPageSize(), MAX_PAGE_SIZE), sort);
        return subscriptionRepository.findAllByFollower_Id(followerId, pageRequest);
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

    public void updateSubscriptionPriorityBecauseOfNewSubscription(Subscription subscription) {
        // TODO: 13.02.2020
    }

    @Transactional
    public void updateSubscriptionPriorityByLike(UUID followerId, UUID followingId) {
        log.debug("Remove one priority of following '{}' in follower '{}' subscriptions.", followingId, followerId);
        subscriptionRepository
            .findFirstByFollower_IdAndFollowing_Id(followerId, followingId)
            .ifPresent(this::updateSubscriptionPriorityByLike);
    }

    private void updateSubscriptionPriorityByLike(Subscription subscription) {
        log.debug("Start update priority of subscription '{}' by like.", subscription.getId());
        var page = PageRequest.of(FIRST_PAGE_NUMBER, MAX_PAGE_SIZE);
        Page<Subscription> subscriptions = getSubscriptions(subscription.getFollower().getId(), page);
        if (subscriptions.getNumberOfElements() == MAX_PAGE_SIZE && !subscriptions.getContent().contains(subscription))
            changePriorityOfLastSubscriptionToMin(subscriptions);

        int updatedPriority = Math.min(subscription.getPriority() + 1, MAX_SUBSCRIPTION_PRIORITY);
        subscription.setPriority(updatedPriority);
        subscriptionRepository.save(subscription);
    }

    private void changePriorityOfLastSubscriptionToMin(Page<Subscription> subscriptions) {
        subscriptions.getContent().stream().reduce((first, last) -> last).ifPresent(lastSubscription -> {
            log.debug("Find last subscription '{}' for change priority to min '{}'.", lastSubscription.getId(), MIN_SUBSCRIPTION_PRIORITY);
            lastSubscription.setPriority(MIN_SUBSCRIPTION_PRIORITY);
            subscriptionRepository.save(lastSubscription);
        });
    }

}
