package com.imagespace.post.web.controller;

import com.imagespace.post.common.dto.LikeDto;
import com.imagespace.post.domain.service.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeController {

    LikeService likeService;

    @GetMapping("/post/{id}")
    public Page<LikeDto> getLikesFromPost(@PathVariable UUID id, Pageable page) {
        return likeService.getPostLikes(id, page).map(LikeDto::new);
    }

    @GetMapping("/post/{id}/count")
    public long getCountLikesFromPost(@PathVariable UUID id) {
        return likeService.getCountOfPostLikes(id);
    }

}
