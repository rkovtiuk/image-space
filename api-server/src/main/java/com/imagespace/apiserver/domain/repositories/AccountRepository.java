package com.imagespace.apiserver.domain.repositories;

import com.imagespace.apiserver.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findOneById(String id);

}
