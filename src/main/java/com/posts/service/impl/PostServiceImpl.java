package com.posts.service.impl;

import com.posts.data.entities.PostEntity;
import com.posts.data.repository.PostRepository;
import com.posts.model.PostDto;
import com.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository
            .findAll()
            .stream()
            .map(PostDto::fromEntity)
            .toList();
    }

    @Transactional
    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity entity = postDto.toEntity();
        return PostDto.fromEntity(postRepository.save(entity));
    }

    @Transactional
    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        PostEntity entity = postDto.toEntity();

        if (postRepository.existsById(id)) {
            return PostDto.fromEntity(postRepository.save(entity));
        } else {
            throw new IllegalArgumentException("Post with id " + entity.getId() + " does not exist.");
        }
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto getPostById(Long postId) {
        return PostDto.fromEntity(postRepository.findById(postId)
                             .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " does not exist.")));
    }
}