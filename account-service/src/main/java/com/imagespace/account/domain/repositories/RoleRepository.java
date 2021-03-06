package com.imagespace.account.domain.repositories;

import com.imagespace.account.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

	Optional<Role> findFirstByName(String name);

}
