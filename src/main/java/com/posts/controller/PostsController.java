package com.posts.controller;

import com.posts.data.entities.PostEntity;
import com.posts.mapper.PostMapper;
import com.posts.model.PostDto;
import com.posts.service.PostService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
@RequestMapping(value = "/api/posts", consumes = "application/json")
public class PostsController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    public @Nonnull Mono<List<PostEntity>> getAllPosts() {
        return postService.getAllPosts().collectList();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<PostDto> createPost(
        @RequestBody
        PostDto postDto) {
        Mono<PostEntity> post = postService.createPost(postMapper.map(postDto));
        return post.map(postMapper::map);
    }

    @PutMapping("/{postId}")
    public Mono<PostDto> updatePost(
        @PathVariable
        Long postId,
        @RequestBody
        PostDto postDto) {
        Mono<PostEntity> updatedPost = postService.updatePost(postId, postMapper.map(postDto));
        return updatedPost.map(postMapper::map);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePost(
        @PathVariable
        Long postId) {
        postService.deletePost(postId);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{postId}")
    public @Nonnull Mono<PostDto> getPostById(
        @PathVariable
        Long postId) {
        return postService.getPostById(postId).map(postMapper::map);
    }
}