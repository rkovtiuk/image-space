package com.imagespace.api.domain.client;

import com.imagespace.api.common.exception.BadRequestException;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AccountFallbackFactory implements FallbackFactory<AccountClient> {

    @Override
    public AccountClient create(Throwable throwable) {
        return username -> {
            FeignException feignException = parseFeignException(throwable);
            if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                return Optional.empty();
            }

            log.error("Can't get account by username '{}'. Details ", username, feignException);
            throw new BadRequestException("Can't get response from account-service");
        };
    }

    private static FeignException parseFeignException(Throwable cause) {
        if (cause instanceof FeignException)
            return (FeignException) cause;

        cause.printStackTrace();
        throw new BadRequestException("Can't get response between services");
    }

}
