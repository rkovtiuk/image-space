package com.imagespace.account.domain.service;

import com.imagespace.account.domain.entity.Role;
import com.imagespace.account.domain.repositories.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Cacheable("roles")
    public Page<Role> findAll(Pageable page) {
        log.debug("Get all roles by: {} .", page);
        return roleRepository.findAll(page);
    }

    @Transactional
    @CacheEvict("roles")
    public Role createRole(String name) {
        log.debug("Add a new role '{}' .", name);
        var role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Transactional
    @CacheEvict("roles")
    public void removeRole(String name) {
        log.debug("Remove role '{}'.", name);
        roleRepository.findFirstByName(name).ifPresent(roleRepository::delete);
    }

}
