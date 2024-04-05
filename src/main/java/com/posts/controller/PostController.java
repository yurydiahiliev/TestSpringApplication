package com.posts.controller;

import com.posts.mapper.PostMapper;
import com.posts.model.PostDto;
import com.posts.service.PostService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public Mono<List<PostDto>> getAllPosts() {
        return postService.getAllPosts().map(postMapper::map).collectList();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<PostDto> createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postMapper.map(postDto)).map(postMapper::map);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postMapper.map(postDto)).map(postMapper::map);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAllPosts() {
        return postService.deleteAllPosts();
    }

    @GetMapping("/{postId}")
    public @Nonnull Mono<PostDto> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId).map(postMapper::map);
    }
}