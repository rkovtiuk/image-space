package com.imagespace.api.domain.client;

import com.imagespace.api.domain.dto.AccountDto;
import com.imagespace.api.domain.dto.RoleDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

@Component
public class CoreFallbackFactory implements FallbackFactory<CoreClient> {

    @Override
    public CoreClient create(Throwable throwable) {
        return new CoreClient() {

            @Override
            public Optional<AccountDto> findAccountById(String accountId) {
                System.out.println(throwable.getMessage());

                return empty();
            }

            @Override
            public Optional<AccountDto> createAccount(AccountDto account) {
                return empty();
            }

            @Override
            public boolean accountExists(String accountId) {
                return true;
            }

            @Override
            public List<RoleDto> findAllByAccount(String accountId) {
                System.out.println(throwable.getMessage());

                return emptyList();
            }
        };
    }

}
