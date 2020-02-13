package com.imagespace.post.web.controller;

import com.imagespace.post.common.dto.PostDto;
import com.imagespace.post.domain.service.FeedsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedsController {

    FeedsService feedsService;

    @GetMapping
    public Collection<PostDto> getFeeds(@RequestParam UUID accountId, Pageable page) {
        return feedsService.getFeedsPost(accountId, page).map(PostDto::new).getContent();
    }

}
