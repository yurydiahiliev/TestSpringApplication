package com.posts.service;

import com.posts.data.entities.PostEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PostService {
    Flux<PostEntity> getAllPosts();
    Mono<PostEntity> createPost(PostEntity postEntity);
    Mono<PostEntity> updatePost(Long id, PostEntity postEntity);
    Mono<Void> deletePost(Long postId);
    Mono<Void> deleteAllPosts();
    Mono<PostEntity> getPostById(Long postId);
}