package com.imagespace.post.domain.client.fallback;

import com.imagespace.post.common.exception.ImageSpaceException;
import com.imagespace.post.domain.client.AccountClient;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;

@Slf4j
@Component
public class AccountFallbackFactory implements FallbackFactory<AccountClient> {

    @Override
    public AccountClient create(Throwable throwable) {
        return (followerId, pageSize, pageNumber) -> {
            FeignException feignException = parseFeignException(throwable);
            if (feignException.status() == HttpStatus.NOT_FOUND.value()) {
                return new PageImpl(emptyList());
            }

            log.error("Can't get subscriptions from account id '{}', page size {} page number {}.", followerId, pageSize, pageNumber);
            throw new ImageSpaceException("Can't get response from account-service", throwable);
        };
    }

    private FeignException parseFeignException(Throwable cause) {
        if (cause instanceof FeignException)
            return (FeignException) cause;

        cause.printStackTrace();
        throw new ImageSpaceException("Can't get response between services", cause);
    }

}
