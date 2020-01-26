package com.imagespace.post.domain.repo;

import com.imagespace.post.domain.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    @EntityGraph(value = "Like.post")
    Optional<Like> findTopByAccountIdAndPost_Id(UUID accountId, UUID postId);

    Long countByPostId(UUID postId);

    Page<Like> findAllByPostId(UUID id, Pageable page);

}
