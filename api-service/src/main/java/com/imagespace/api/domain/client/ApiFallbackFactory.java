package com.imagespace.api.domain.client;

import com.imagespace.api.common.exception.BadRequestException;
import feign.FeignException;

public interface ApiFallbackFactory {

    default FeignException parseFeignException(Throwable cause) {
        if (cause instanceof FeignException)
            return (FeignException) cause;

        cause.printStackTrace();
        throw new BadRequestException("Can't get response between services");
    }

}
