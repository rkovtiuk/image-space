package com.imagespace.post.web.controller;

import com.imagespace.post.common.dto.LikeDto;
import com.imagespace.post.common.dto.PostDto;
import com.imagespace.post.common.exception.HttpExceptionBuilder;
import com.imagespace.post.domain.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @GetMapping
    public Page<PostDto> getAccountPosts(@RequestParam List<UUID> accounts, Pageable page) {
        log.info("Getting posts from account {} with params: {}", accounts, page);
        return postService.getAccountPosts(accounts, page).map(PostDto::new);
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable UUID postId) {
        log.info("Getting post {}", postId);
        return postService.getPost(postId)
            .map(PostDto::new)
            .orElseThrow(() -> HttpExceptionBuilder.notFound(String.format("Not found post with id %s", postId)));
    }

    @PostMapping("/{id}/likes")
    public void putLike(@PathVariable UUID id, @RequestBody LikeDto like) {
        log.info("Put like from account {} to post {}", like.getAccountId(), id);
        postService.addLikeToPost(id, like.getAccountId());
    }

    @DeleteMapping("/{id}/likes")
    public void removeLike(@PathVariable UUID id, @RequestBody LikeDto like) {
        log.info("Remove like from account {} to post {}", like.getAccountId(), id);
        postService.removeLikeFromPost(id, like.getAccountId());
    }

    @GetMapping("/{id}/likes")
    public Page<LikeDto> getLikesFromPost(@PathVariable UUID id, Pageable page) {
        log.info("Get all like from post {} with params: {}", id, page);
        return postService.getPostLikes(id, page).map(LikeDto::new);
    }

    @GetMapping("/{id}/count")
    public long getCountOfLikesFromPost(@PathVariable UUID id) {
        log.info("Get count of likes from post {}", id);
        return postService.getCountOfPostLikes(id);
    }

}
