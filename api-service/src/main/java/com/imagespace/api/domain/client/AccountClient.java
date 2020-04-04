package com.imagespace.api.domain.client;

import com.imagespace.api.common.dto.AccountDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RibbonClient("account-service")
@FeignClient(value = "account-service", fallbackFactory = AccountFallbackFactory.class)
public interface AccountClient {

    @GetMapping("/accounts/{username}")
    Optional<AccountDto> findAccount(@PathParam("username") String username);

}
