package com.imagespace.post.domain.repo;

import com.imagespace.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findAllByAccountIdIn(List<UUID> accountId, Pageable page);

    Optional<Post> findPostByIdAndAccountId(UUID postId, UUID accountId);

}
