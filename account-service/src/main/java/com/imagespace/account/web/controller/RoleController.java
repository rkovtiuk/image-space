package com.imagespace.account.web.controller;

import com.imagespace.account.common.dto.RoleDto;
import com.imagespace.account.common.exception.HttpExceptionBuilder;
import com.imagespace.account.domain.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;


    @GetMapping
    public Page<RoleDto> getRoles(Pageable page) {
        return roleService.findAll(page).map(RoleDto::new);
    }

    @PostMapping
    public RoleDto createRole(@RequestBody RoleDto role) {
        return Optional.ofNullable(role)
            .map(RoleDto::getName)
            .map(roleService::createRole)
            .map(RoleDto::new)
            .orElseThrow(() -> HttpExceptionBuilder.badRequest("Can't create a new role. Incorrect Request Body."));
    }

}
