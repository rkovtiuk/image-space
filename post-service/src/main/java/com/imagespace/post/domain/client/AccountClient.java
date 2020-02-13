package com.imagespace.post.domain.client;

import com.imagespace.post.common.dto.SubscriptionDto;
import com.imagespace.post.domain.client.fallback.AccountFallbackFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RibbonClient("account-service")
@FeignClient(value = "account-service", fallbackFactory = AccountFallbackFactory.class)
public interface AccountClient {

    @GetMapping("/subscriptions/{followerId}")
    Page<SubscriptionDto> getSubscription(@PathVariable UUID followerId,
                                          @RequestParam Long pageSize,
                                          @RequestParam Long pageNumber);

}
