package com.imagespace.post.domain.service;

import com.imagespace.post.domain.entity.Like;
import com.imagespace.post.domain.repo.LikeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeService {

    LikeRepository likeRepository;

    public long getCountOfPostLikes(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    public Page<Like> getPostLikes(UUID id, Pageable page) {
        return likeRepository.findAllByPostId(id, page);
    }
}
