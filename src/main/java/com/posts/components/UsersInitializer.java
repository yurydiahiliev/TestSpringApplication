package com.posts.components;

import com.posts.model.UserDto;
import com.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userService.existsByUsername("admin")) {
            UserDto adminUserEntity = UserDto.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .build();
            userService.add(adminUserEntity);
            System.out.println("<======= Init default admin user ========>");
        }
    }
}