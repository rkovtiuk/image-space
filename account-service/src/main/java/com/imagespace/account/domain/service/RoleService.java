package com.imagespace.account.domain.service;

import com.imagespace.account.domain.entity.Role;
import com.imagespace.account.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleService {

    private final RoleRepository roleRepository;

    @Cacheable("roles")
    public List<Role> findAllByAccount(String accountId) {
        return roleRepository.findAllByAccount_Id(accountId);
    }
}
