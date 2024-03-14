package com.posts.mapper;

import com.posts.data.entities.PostEntity;
import com.posts.model.PostDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto map(PostEntity postEntity);
    @InheritInverseConfiguration
    PostEntity map(PostDto dto);
}
