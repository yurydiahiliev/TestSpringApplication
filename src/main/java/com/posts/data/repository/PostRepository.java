package com.posts.data.repository;

import com.posts.data.entities.PostEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends R2dbcRepository<PostEntity, Long> {

}

