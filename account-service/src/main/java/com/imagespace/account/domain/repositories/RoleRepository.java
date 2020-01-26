package com.imagespace.account.domain.repositories;

import com.imagespace.account.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByAccount_Id(UUID accountId);

}
