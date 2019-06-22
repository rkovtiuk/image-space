package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Role;
import com.imagespace.core.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAllByAccount(String accountId) {
        return roleRepository.findAllByAccount_Id(accountId);
    }
}
