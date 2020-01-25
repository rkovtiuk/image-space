package com.imagespace.post.domain.repo;

import com.imagespace.post.domain.entity.Like;
import com.imagespace.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    Optional<Like> findByIdAndAccountIdAndPostId(UUID likeId, UUID accountId, UUID postId);

    Collection<Like> findAllByPost(Post post);

    Long countByPost(Post post);

}
