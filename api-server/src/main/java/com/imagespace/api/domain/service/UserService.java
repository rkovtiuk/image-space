package com.imagespace.api.domain.service;

import com.imagespace.api.domain.client.CoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final CoreClient coreClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return coreClient.findAccountById(username)
            .map(account -> new User(username, account.getPassword(), getAuthority(account.getId())))
            .orElseThrow(() -> new UsernameNotFoundException("Invalid account"));
    }

    private List<SimpleGrantedAuthority> getAuthority(String accountId) {
        return coreClient.findAllByAccount(accountId).stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

}