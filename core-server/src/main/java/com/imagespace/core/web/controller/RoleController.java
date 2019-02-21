package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Role;
import com.imagespace.core.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles/{accountId}")
    public List<Role> findAllUserRoles(@PathVariable String accountId) {
        return roleService.findAllByAccount(accountId);
    }

}
