package com.imagespace.account.domain.repositories;

import com.imagespace.account.domain.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @EntityGraph("Account.roles")
    Optional<Account> findOneByUsername(String username);

}
