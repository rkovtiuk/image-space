package com.imagespace.core.domain.repositories;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByAccount(Account account);

}
