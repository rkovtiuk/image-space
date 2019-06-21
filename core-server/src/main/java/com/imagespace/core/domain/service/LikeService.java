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
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeService {

    LikeRepository likeRepository;

    public void like(UUID postId, LikeDto dto) {
        var like = new Like(UUID.randomUUID(), new Post().setId(postId), new Account().setId(dto.getAccountId()));
        likeRepository.save(like);
    }

    public void dislike(UUID postId, LikeDto dto) {
        likeRepository
                .findByIdAndAccountIdAndPostId(dto.getLikeId(), dto.getAccountId(), postId)
                .ifPresent(likeRepository::delete);
    }

    public void deletePostLikes(Post post) {
        likeRepository
                .findAllByPost(post)
                .forEach(likeRepository::delete);
    }

    public long countOfPostLikes(Post post) {
        return likeRepository.countByPost(post);
    }
}
