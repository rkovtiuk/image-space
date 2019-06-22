package com.imagespace.core.domain.repositories;

import com.imagespace.core.domain.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, UUID> {

    Optional<Subscribe> findFirstBySubscriberIdAndSubscribingId(UUID subscriberId, UUID subscribingId);

    Page<Subscribe> findAllBySubscribingId(UUID subscribingId, Pageable pageable);

}
