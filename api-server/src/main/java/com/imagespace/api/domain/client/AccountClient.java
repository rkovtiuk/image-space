package com.imagespace.api.domain.client;

import com.imagespace.api.domain.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("core-server")
public interface AccountClient {

    @GetMapping("/accounts/{accountId}")
    Optional<AccountDto> findAccountById(@PathVariable String accountId);
}
