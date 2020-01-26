package com.imagespace.post.domain.service;

import com.imagespace.post.domain.entity.Like;
import com.imagespace.post.domain.entity.Post;
import com.imagespace.post.domain.repo.LikeRepository;
import com.imagespace.post.domain.repo.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    int MIN_LIKES_COUNT = 0;

    PostRepository postRepository;
    LikeRepository likeRepository;

    public Optional<Post> getPost(UUID postId) {
        log.info("Getting post by id '{}'.", postId);
        return postRepository.findById(postId);
    }

    public Page<Post> getAccountPosts(List<UUID> accountIds, Pageable pageable) {
        var page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        log.info("Getting posts from with params: '{}' from accounts: '{}'.", page, accountIds);
        return postRepository.findAllByAccountIdIn(accountIds, page);
    }

    public long getCountOfPostLikes(UUID postId) {
        log.info("Getting count of likes from post '{}'.", postId);
        return likeRepository.countByPostId(postId);
    }

    public Page<Like> getPostLikes(UUID id, Pageable page) {
        log.info("Getting likes from post '{}' with params: '{}'.", id, page);
        return likeRepository.findAllByPostId(id, page);
    }

    @Transactional
    public void addLikeToPost(UUID postId, UUID accountId) {
        log.info("Adding like to post '{}' from account '{}'.", postId, accountId);
        likeRepository.findTopByAccountIdAndPost_Id(accountId, postId)
            .or(() -> of(new Like(UUID.randomUUID(), accountId, new Post(postId))))
            .map(likeRepository::save)
            .ifPresent(like -> {
                Post post = like.getPost();
                long likes = post.getLikes() + 1;
                log.info("After adding like to post '{}' from account '{}', the count of likes increased from '{}' to '{}'.", postId, accountId, post.getLikes(), likes);
                post.setLikes(likes);
                postRepository.save(post);
            });
    }

    @Transactional
    public void removeLikeFromPost(UUID postId, UUID accountId) {
        log.info("Removing like of post '{}' from account '{}'.", postId, accountId);
        likeRepository.findTopByAccountIdAndPost_Id(accountId, postId)
            .ifPresent(like -> {
                Post post = like.getPost();
                long likes = post.getLikes() > 0 ? post.getLikes() - 1 : MIN_LIKES_COUNT;
                log.info("After removing like of post '{}' from account '{}', the count of likes decreases from '{}' to '{}'.", postId, accountId, post.getLikes(), likes);
                post.setLikes(likes);

                postRepository.save(post);
                likeRepository.delete(like);
            });
    }

    @Transactional
    public Post createPost(UUID sourceId, UUID accountId) {
        var postId = UUID.randomUUID();
        log.info("Creating a post '{}' from account '{}'.", postId, accountId);
        return postRepository.save(new Post(postId, sourceId, MIN_LIKES_COUNT, accountId));
    }

    @Transactional
    public void deletePost(UUID postId, UUID accountId) {
        log.info("Deleting a post '{}' in account '{}'.", postId, accountId);
        postRepository
            .findPostByIdAndAccountId(postId, accountId)
            .ifPresentOrElse(
                postRepository::delete,
                () -> log.error("Can't find post '{}' of account '{}' for delete.", postId, accountId));
    }

}
