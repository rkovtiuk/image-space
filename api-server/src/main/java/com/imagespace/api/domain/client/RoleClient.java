package com.imagespace.api.domain.client;

import com.imagespace.api.domain.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "core-server")
public interface RoleClient {

    @GetMapping("/roles/{accountId}")
    List<RoleDto> findAllByAccount(String accountId);

}
