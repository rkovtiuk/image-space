package com.imagespace.core.domain.service;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Post;
import com.imagespace.core.domain.repositories.PostRepository;
import com.imagespace.core.web.dto.LikeDto;
import com.imagespace.core.web.dto.PostDto;
import com.imagespace.core.web.exception.HttpExceptionBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    static long STARTED_LIKES_COUNT = 0L;

    PostRepository postRepository;
    LikeService likeService;

    @Transactional
    public Post createPost(PostDto dto) {
        var post = new Post(UUID.randomUUID(), dto.getSourceId(), STARTED_LIKES_COUNT, new Account().setId(dto.getAccountId()));
        log.debug("Creating a post {}", post.getId());
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(PostDto dto) {
        log.debug("Updating post with id {}", dto.getId());
        return findPostForAccount(dto)
                .map(post -> post.setSource(dto.getSourceId()))
                .map(postRepository::save)
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find post in this account"));
    }

    @Transactional
    public void deletePost(PostDto dto) {
        log.debug("Deleting a post {} in account {}", dto.getId(), dto.getAccountId());
        var post = findPostForAccount(dto)
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find post in this account"));

        likeService.deletePostLikes(post);
        postRepository.delete(post);
    }

    @Transactional
    public void likePost(UUID postId, LikeDto dto) {
        log.debug("Adding like from post {} and updating count of likes", postId);
        postRepository
                .findById(postId)
                .ifPresent(post -> {
                    likeService.like(postId, dto);
                    updatePostLikes(post);
                });
    }

    @Transactional
    public void dislikePost(UUID postId, LikeDto dto) {
        log.debug("Removing like from post {} and updating count of likes", postId);
        postRepository
                .findById(postId)
                .ifPresent(post -> {
                    likeService.dislike(postId, dto);
                    updatePostLikes(post);
                });
    }

    private Optional<Post> findPostForAccount(PostDto dto) {
        return postRepository
                .findById(dto.getId())
                .filter(post -> checkThatPostRelatedToAccount(post, dto.getAccountId()));
    }

    private boolean checkThatPostRelatedToAccount(Post post, UUID accountId) {
        if (!post.getAccount().getId().equals(accountId)) {
            log.error("Can't update or delete post {} of account {}. Used another account for operation {}.",
                    post.getId(), post.getAccount().getId(), accountId);
            return false;
        }

        return true;
    }

    private void updatePostLikes(Post post) {
        var likesCount = likeService.countOfPostLikes(post);
        post.setLikes(likesCount);
        postRepository.save(post);
    }
}
