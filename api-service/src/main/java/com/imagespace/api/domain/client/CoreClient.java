package com.imagespace.api.domain.client;

import com.imagespace.api.dto.AccountDto;
import com.imagespace.api.dto.RoleDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RibbonClient("account-service")
@FeignClient(value = "account-service", fallbackFactory = CoreFallbackFactory.class)
public interface CoreClient {

    // account
    @GetMapping("/accounts/{accountId}")
    Optional<AccountDto> findAccountById(@PathVariable String accountId);

    @PostMapping("/accounts")
    Optional<AccountDto> createAccount(@RequestBody AccountDto account);

    @GetMapping("/accounts/{accountId}/exists")
    boolean accountExists(@PathVariable String accountId);

    // role
    @GetMapping("/roles/{accountId}")
    List<RoleDto> findAllByAccount(String accountId);

}
