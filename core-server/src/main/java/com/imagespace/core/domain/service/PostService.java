package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Post;
import com.imagespace.core.domain.repositories.PostRepository;
import com.imagespace.core.web.dto.PostDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    static int STARTED_LIKES_COUNT = 0;

    PostRepository postRepository;

    @Transactional
    public Post createPost(PostDto dto) {
        var createdPost = new Post(UUID.randomUUID(), dto.getSourceId(), STARTED_LIKES_COUNT, new Account().setId(dto.getAccountId()));
        log.info("Creating a post {}", createdPost.getId());
        return postRepository.save(createdPost);
    }

    @Transactional
    public Post updatePost(PostDto post) {
        return postRepository.findById(post.getId())
            .filter(entity -> checkThatPostRelatedToAccount(entity, post.getAccountId()))
            .map(entity -> entity.setSource(post.getSourceId()))
            .map(postRepository::save)
            .orElse(null);
    }

    @Transactional
    public void deletePost(PostDto post) {
        postRepository.findById(post.getId())
                .filter(entity -> checkThatPostRelatedToAccount(entity, post.getAccountId()))
                .ifPresent(postRepository::delete);
    }

    private boolean checkThatPostRelatedToAccount(Post post, UUID accountId) {
        if (!post.getAccount().getId().equals(accountId)) {
            log.error("Can't update or delete post {} of account {}. Used another account for operation {}.",
                    post.getId(), post.getAccount().getId(), accountId);
            return false;
        }

        return true;
    }

}
