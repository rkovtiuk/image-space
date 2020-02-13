package com.imagespace.post.domain.service;

import com.imagespace.post.common.dto.SubscriptionDto;
import com.imagespace.post.domain.client.AccountClient;
import com.imagespace.post.domain.entity.Post;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedsService {

    AccountClient accountClient;
    PostService postService;

    long ACCOUNT_PAGE_NUMBER = 1;
    long ACCOUNT_PAGE_SIZE = 500;

    public Page<Post> getFeedsPost(UUID accountId, Pageable page) {
        Page<SubscriptionDto> subscriptions = accountClient.getSubscription(accountId, ACCOUNT_PAGE_SIZE, ACCOUNT_PAGE_NUMBER);
        List<UUID> accounts = subscriptions.map(SubscriptionDto::getFollowing).getContent();
        return postService.getAccountPosts(accounts, page);
    }

}
