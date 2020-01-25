package com.imagespace.post.domain.service;

import com.imagespace.post.domain.entity.Post;
import com.imagespace.post.domain.repo.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostRepository postRepository;

    public Optional<Post> getPost(UUID postId) {
        return postRepository.findById(postId);
    }

    public Page<Post> getAccountPosts(UUID accountId, Pageable page) {
        return postRepository.findAllByAccountId(accountId, page);
    }

}
