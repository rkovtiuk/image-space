package com.imagespace.api.config.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
@ConfigurationProperties(prefix = "security.properties")
public class SecurityConfigProperties {

    String clientId;

    String clientSecret;

    String grantTypePassword;

    String authorizationCode;

    String refreshToken;

    String implicit;

    String scopeRead;

    String scopeWrite;

    String trust;

    int accessTokenValiditySeconds;

    int refreshTokenValiditySeconds;

}


