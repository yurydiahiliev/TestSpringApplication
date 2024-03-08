package com.posts.service;

import com.posts.model.Post;

import java.util.*;

public interface PostService {
    List<Post> getAllPosts();
    Post createPost(Post post);
    Post updatePost(Post post);
    void deletePost(Long postId);
    Post getPostById(Long postId);
}
