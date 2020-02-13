package com.imagespace.post.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Can't get information between services")
public class ImageSpaceException extends RuntimeException {

    public ImageSpaceException(String message, Throwable cause) {
        super(message, cause);
    }

}
