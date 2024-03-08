package com.posts.components;

import com.posts.model.Users;
import com.posts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            Users adminUser = new Users("admin", passwordEncoder.encode("admin"));
            userRepository.save(adminUser);
            System.out.println("<======= Init default admin user ========>");
        }
    }
}