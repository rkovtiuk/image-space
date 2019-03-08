package com.imagespace.core.domain.repositories;

import com.imagespace.core.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findOneById(String id);

    boolean existsAccountById(String id);

}
