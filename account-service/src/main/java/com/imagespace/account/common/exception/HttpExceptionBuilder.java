package com.imagespace.account.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class HttpExceptionBuilder {

    private static final String BAD_REQUEST_STATUS = "Not valid parameters";
    private static final String NOT_FOUND_STATUS = "Not found";
    private static final HttpHeaders HEADERS = new HttpHeaders();
    private static final Charset DEFAULT_CHARSET = null;

    public static HttpClientErrorException badRequest(String msg) {
        return HttpClientErrorException.create(HttpStatus.BAD_REQUEST, BAD_REQUEST_STATUS, HEADERS, msg.getBytes(), DEFAULT_CHARSET);
    }

    public static HttpClientErrorException notFound(String msg) {
        return HttpClientErrorException.create(HttpStatus.NOT_FOUND, NOT_FOUND_STATUS, HEADERS, msg.getBytes(), DEFAULT_CHARSET);
    }

}
