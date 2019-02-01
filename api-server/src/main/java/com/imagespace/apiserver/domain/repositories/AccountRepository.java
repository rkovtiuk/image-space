package com.imagespace.apiserver.domain.repositories;

import com.imagespace.apiserver.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {



}
