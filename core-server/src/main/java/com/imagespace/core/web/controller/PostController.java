package com.imagespace.core.web.controller;

import com.imagespace.core.domain.service.PostService;
import com.imagespace.core.web.dto.LikeDto;
import com.imagespace.core.web.dto.PostDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @PostMapping
    public PostDto createPost(@RequestBody PostDto dto) {
        log.info("Creating a post for account {}", dto.getAccountId());
        return new PostDto(postService.createPost(dto));
    }

    @PutMapping
    public PostDto updatePost(@RequestBody PostDto dto) {
        log.info("Update a post {} for account {}", dto.getId(), dto.getAccountId());
        return new PostDto(postService.updatePost(dto));
    }

    @DeleteMapping
    public void deletePost(@RequestBody PostDto dto) {
        log.info("Deleting a post {} for account {}", dto.getId(), dto.getAccountId());
        postService.deletePost(dto);
    }

    @PostMapping("/{postId}/like")
    public void like(@PathVariable UUID postId, @RequestBody LikeDto dto) {
        postService.likePost(postId, dto);
    }

    @DeleteMapping("/{postId}/like")
    public void dislike(@PathVariable UUID postId, @RequestBody LikeDto dto) {
        postService.dislikePost(postId, dto);
    }

}
