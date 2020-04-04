package com.imagespace.api.domain.service;

import com.imagespace.api.common.dto.AccountDto;
import com.imagespace.api.domain.client.AccountClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements UserDetailsService {

    AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountClient.findAccount(username)
            .map(account -> new User(username, account.getPassword(), getAuthority(account)))
            .orElseThrow(() -> new UsernameNotFoundException("Invalid account"));
    }

    private List<SimpleGrantedAuthority> getAuthority(AccountDto account) {
        return account.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
