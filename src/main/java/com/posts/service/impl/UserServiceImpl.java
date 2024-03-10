package com.posts.service.impl;

import com.posts.data.entities.UserEntity;
import com.posts.data.repository.UserRepository;
import com.posts.exeptions.NotFoundException;
import com.posts.model.UserDto;
import com.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return UserDto.fromEntity(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findUserById(Long id) {
        return UserDto.fromEntity(
            userRepository.findById(id).orElseThrow(() -> new NotFoundException("Can not find user by id: " + id)));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public UserDto add(UserDto userDto) {
        return UserDto.fromEntity(userRepository.save(userDto.toEntity()));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}