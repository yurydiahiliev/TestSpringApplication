package com.posts.service.impl;

import com.posts.data.entities.PostEntity;
import com.posts.data.repository.PostRepository;
import com.posts.exceptions.ApiException;
import com.posts.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Flux<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Mono<PostEntity> createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public Mono<PostEntity> updatePost(Long id, PostEntity postEntity) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiException(format("Cannot find post by id: %d", id), HttpStatus.NOT_FOUND)))
                .flatMap(existingPost -> {
                    existingPost.setTitle(postEntity.getTitle());
                    existingPost.setContent(postEntity.getContent());
                    return postRepository.save(existingPost)
                            .doOnSuccess(u -> log.info("Post - post: {} updated", u));
                });
    }

    @Override
    public Mono<Void> deletePost(Long postId) {
        return postRepository.deleteById(postId)
                .doOnError(error -> {
                    throw new ApiException(format("Cannot delete post with id: %d", postId), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @Override
    public Mono<Void> deleteAllPosts() {
        return postRepository.deleteAll().then();
    }

    @Override
    public Mono<PostEntity> getPostById(Long postId) {
        return postRepository.findById(postId)
                .switchIfEmpty(Mono.error(new ApiException("Post with id " + postId + " does not exist.", HttpStatus.NOT_FOUND)));
    }
}