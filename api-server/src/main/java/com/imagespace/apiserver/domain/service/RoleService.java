package com.imagespace.apiserver.domain.service;

import com.imagespace.apiserver.domain.entity.Account;
import com.imagespace.apiserver.domain.entity.Role;
import com.imagespace.apiserver.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAllByAccount(Account account) {
        return roleRepository.findAllByAccount(account);
    }
}
