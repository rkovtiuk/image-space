package com.imagespace.api.config.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Data
@Configuration
@EnableAuthorizationServer
@EqualsAndHashCode(callSuper = true)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final SecurityConfigProperties properties;

    private final AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        var converter = new JwtAccessTokenConverter();
        converter.setSigningKey("as466gf");
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
            .inMemory()
            .withClient(properties.getClientId())
            .secret(properties.getClientSecret())
            .authorizedGrantTypes(
                properties.getGrantTypePassword(),
                properties.getAuthorizationCode(),
                properties.getRefreshToken(),
                properties.getImplicit())
            .scopes(
                properties.getScopeRead(),
                properties.getScopeWrite())
            .accessTokenValiditySeconds(properties.getAccessTokenValiditySeconds())
            .refreshTokenValiditySeconds(properties.getRefreshTokenValiditySeconds());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter());
    }
}
