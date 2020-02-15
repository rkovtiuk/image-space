package com.imagespace.account.domain.repositories;

import com.imagespace.account.domain.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> findFirstByFollower_IdAndFollowing_Id(UUID followerId, UUID followingId);

    @EntityGraph(attributePaths = {"following"})
    Page<Subscription> findAllByFollower_Id(UUID followerId, Pageable pageable);

}
