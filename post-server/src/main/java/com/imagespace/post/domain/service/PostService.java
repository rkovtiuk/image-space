package com.imagespace.post.domain.service;

import com.imagespace.post.domain.entity.Like;
import com.imagespace.post.domain.entity.Post;
import com.imagespace.post.domain.repo.LikeRepository;
import com.imagespace.post.domain.repo.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    int MIN_LIKES_COUNT = 0;

    PostRepository postRepository;
    LikeRepository likeRepository;

    public Optional<Post> getPost(UUID postId) {
        return postRepository.findById(postId);
    }

    public Page<Post> getAccountPosts(List<UUID> accountId, Pageable pageable) {
        var page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAllByAccountIdIn(accountId, page);
    }

    public long getCountOfPostLikes(UUID postId) {
        return likeRepository.countByPostId(postId);
    }

    public Page<Like> getPostLikes(UUID id, Pageable page) {
        return likeRepository.findAllByPostId(id, page);
    }

    @Transactional
    public void addLikeToPost(UUID postId, UUID accountId) {
        likeRepository.findTopByAccountIdAndPost_Id(accountId, postId)
            .or(() -> of(new Like(UUID.randomUUID(), accountId, new Post(postId))))
            .map(likeRepository::save)
            .ifPresent(like -> {
                Post post = like.getPost();
                long likes = post.getLikes() + 1;
                post.setLikes(likes);

                postRepository.save(post);
            });
    }

    @Transactional
    public void removeLikeFromPost(UUID postId, UUID accountId) {
        likeRepository.findTopByAccountIdAndPost_Id(accountId, postId)
            .ifPresent(like -> {
                Post post = like.getPost();
                long likes = post.getLikes() > 0 ? post.getLikes() - 1 : MIN_LIKES_COUNT;
                post.setLikes(likes);

                postRepository.save(post);
                likeRepository.delete(like);
            });
    }
}
