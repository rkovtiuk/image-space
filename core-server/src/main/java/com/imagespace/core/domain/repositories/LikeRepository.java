package com.imagespace.core.domain.repositories;

import com.imagespace.core.domain.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

}
