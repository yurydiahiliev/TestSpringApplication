package com.posts.service.impl;

import com.posts.data.entities.PostEntity;
import com.posts.data.repository.PostRepository;
import com.posts.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<PostEntity> updatePost(Long id, PostEntity postDto) {
        return postRepository.findById(id)
                             .flatMap(existingPost -> {
                                 existingPost.setTitle(postDto.getTitle());
                                 existingPost.setContent(postDto.getContent());
                                 return postRepository.save(existingPost);
                             });
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
    
    @Override
    public Mono<PostEntity> getPostById(Long postId) {
        return postRepository.findById(postId)
                             .switchIfEmpty(Mono.error(new IllegalArgumentException("Post with id " + postId + " does not exist.")));
    }
}