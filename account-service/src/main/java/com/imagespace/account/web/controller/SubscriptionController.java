package com.imagespace.account.web.controller;

import com.imagespace.account.common.dto.AccountBaseDto;
import com.imagespace.account.common.dto.SubscriptionDto;
import com.imagespace.account.domain.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionController {

    SubscriptionService subscriptionService;

    @GetMapping("/{followerId}")
    public Page<SubscriptionDto> getSubscriptionDto(@PathVariable UUID followerId, Pageable page) {
        return subscriptionService
            .getSubscriptions(followerId, page)
            .map(sub -> new SubscriptionDto(sub.getId(), sub.getFollower().getId(), sub.getFollowing().getId()));
    }

    @GetMapping("/{followerId}/accounts")
    public Page<AccountBaseDto> getSubscriptionsAccounts(@PathVariable UUID followerId, Pageable page) {
        return subscriptionService
            .getSubscriptionsAccounts(followerId, page)
            .map(account -> new AccountBaseDto(account.getId(), account.getUsername(), account.getAvatar()));
    }

    @PostMapping
    public SubscriptionDto createSubscription(@RequestBody SubscriptionDto subscription) {
        var newSubs = subscriptionService.follow(subscription.getFollower(), subscription.getFollowing());
        return new SubscriptionDto(newSubs.getId(), newSubs.getFollower().getId(), newSubs.getFollowing().getId());
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<?> removeSubscription(@RequestBody SubscriptionDto subscription) {
        subscriptionService.unfollow(subscription.getFollower(), subscription.getFollowing());
        return ResponseEntity.ok().build();
    }

}
