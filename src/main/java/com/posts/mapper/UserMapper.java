package com.posts.mapper;

import com.posts.data.entities.UserEntity;
import com.posts.model.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(UserEntity userEntity);
    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}