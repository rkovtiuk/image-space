package com.imagespace.api.domain.client;

import com.imagespace.api.common.dto.AccountDto;
import com.imagespace.api.common.dto.RoleDto;
import com.imagespace.api.common.exception.BadRequestException;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

@Slf4j
@Component
public class AccountFallbackFactory implements FallbackFactory<AccountClient>, ApiFallbackFactory {

    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {

            @Override
            public Optional<AccountDto> findAccountById(String accountId) {
                FeignException feignException = parseFeignException(throwable);
                if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                    return empty();
                }

                log.error("Can't get account by id '{}'. Details ", accountId, feignException);
                throw new BadRequestException("Can't get response from account-service");
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
                FeignException feignException = parseFeignException(throwable);
                if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                    return emptyList();
                }

                log.error("Can't get account roles by id '{}'. Details ", accountId, feignException);
                throw new BadRequestException("Can't get response from account-service");
            }
        };
    }

}
