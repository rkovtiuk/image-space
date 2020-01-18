package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Like;
import com.imagespace.core.domain.entity.Post;
import com.imagespace.core.domain.repositories.LikeRepository;
import com.imagespace.core.web.dto.LikeDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeService {

    LikeRepository likeRepository;

    public void like(UUID postId, LikeDto dto) {
        var like = new Like(UUID.randomUUID(), new Post().setId(postId), new Account().setId(dto.getAccountId()));
        log.debug("Making like with id {} on post {} from account {}.", like.getId(), postId, dto.getAccountId());
        likeRepository.save(like);
    }

    public void dislike(UUID postId, LikeDto dto) {
        log.debug("Removing like with id {} on post {} from account {}", dto.getLikeId(), postId, dto.getAccountId());
        likeRepository
                .findByIdAndAccountIdAndPostId(dto.getLikeId(), dto.getAccountId(), postId)
                .ifPresent(likeRepository::delete);
    }

    public void deletePostLikes(Post post) {
        log.debug("Removing all likes from post {}", post.getId());
        likeRepository.findAllByPost(post).forEach(likeRepository::delete);
    }

    public long countOfPostLikes(Post post) {
        log.debug("Getting count of likes from post {}", post.getId());
        return likeRepository.countByPost(post);
    }
}
