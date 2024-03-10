package com.posts.controller;

import com.posts.model.PostDto;
import com.posts.service.PostService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/posts", consumes = "application/json")
public class PostsController {

    @Autowired
    private PostService postService;

    @GetMapping
    public @Nonnull List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostDto createPost(@RequestBody PostDto postEntity) {
        return postService.createPost(postEntity);
    }

    @PutMapping("/{postId}")
    public PostDto updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{postId}")
    public @Nonnull PostDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }
}
