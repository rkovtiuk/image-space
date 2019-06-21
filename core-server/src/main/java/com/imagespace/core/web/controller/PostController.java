package com.imagespace.core.web.controller;

import com.imagespace.core.domain.entity.Account;
import com.imagespace.core.domain.entity.Post;
import com.imagespace.core.domain.service.AccountService;
import com.imagespace.core.domain.service.PostService;
import com.imagespace.core.web.dto.PostDto;
import com.imagespace.core.web.exception.HttpExceptionBuilder;
import com.imagespace.core.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;
    AccountService accountService;

    @PostMapping
    public PostDto createPost(@RequestBody PostDto post) {
        log.info("Creating a post for account {}", post.getAccountId());
        Post createdPost = postService.createPost(post);
        return new PostDto(createdPost.getId(), createdPost.getAccount().getId(), createdPost.getSource(), createdPost.getLikes());
    }

    @PutMapping
    public PostDto updatePost(@RequestBody PostDto post) {
        log.info("Update a post {} for account {}", post.getId(), post.getAccountId());
        return Optional.ofNullable(postService.updatePost(post))
                .map(entity -> new PostDto(entity.getId(), entity.getAccount().getId(), entity.getSource(), entity.getLikes()))
                .orElseThrow(() -> HttpExceptionBuilder.notFound("Can't find post in this account"));

    }

    @DeleteMapping
    public void deltePost(@RequestBody PostDto post) {
        log.info("Deleting a post {} in account {}", post.getId(), post.getAccountId());
        postService.deletePost(post);
    }

}
