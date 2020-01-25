package com.imagespace.post.web.controller;

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

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @GetMapping
    public Page<PostDto> getAccountPosts(@RequestParam UUID account, Pageable page) {
        log.info("Getting posts from account {} with params: {}", account, page);
        return postService.getAccountPosts(account, page).map(PostDto::new);
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable UUID postId) {
        log.info("Getting post {}", postId);
        return postService.getPost(postId)
            .map(PostDto::new)
            .orElseThrow(() -> HttpExceptionBuilder.notFound(String.format("Not found post with id %s", postId)));
    }

}
