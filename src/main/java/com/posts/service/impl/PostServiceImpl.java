package com.posts.service.impl;

import com.posts.model.Post;
import com.posts.repository.PostRepository;
import com.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        if (postRepository.existsById(post.getId())) {
            return postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post with id " + post.getId() + " does not exist.");
        }
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                             .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist."));
    }
}