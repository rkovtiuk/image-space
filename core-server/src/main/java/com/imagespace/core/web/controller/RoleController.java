package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Role;
import com.imagespace.core.domain.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;


    @GetMapping("/roles/{accountId}")
    public List<Role> findAllUserRoles(@PathVariable String accountId) {
        return roleService.findAllByAccount(accountId);
    }

}
