package com.imagespace.apiserver.domain.service;

import com.imagespace.apiserver.domain.entity.Account;
import com.imagespace.apiserver.domain.entity.Role;
import com.imagespace.apiserver.domain.repositories.AccountRepository;
import com.imagespace.apiserver.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TestService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        var rkovtiuk = new Account().setId("rkovtiuk").setPassword("$2a$04$kN0w.hOBs4fIndbQmXC/juSthuCqmFzWWRupHmZXeNbtr4C1hKq.W");
        accountRepository.save(rkovtiuk);

        var role = new Role().setName(Role.RoleType.USER).setAccount(rkovtiuk);
        roleRepository.save(role);
    }


}
