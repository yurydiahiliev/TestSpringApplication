package com.posts.service;

import com.posts.model.PostDto;

import java.util.*;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto createPost(PostDto postEntity);
    PostDto updatePost(Long id, PostDto postEntity);
    void deletePost(Long postId);
    PostDto getPostById(Long postId);
}
