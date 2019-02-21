package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
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

    private final AccountService accountService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountService.findAccountById(username)
            .map(account -> new User(username, account.getPassword(), getAuthority(account)))
            .orElseThrow(() -> new UsernameNotFoundException("Invalid account"));
    }

    private List<SimpleGrantedAuthority> getAuthority(Account account) {
        return roleService.findAllByAccount(account).stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
            .collect(Collectors.toList());
    }

}
