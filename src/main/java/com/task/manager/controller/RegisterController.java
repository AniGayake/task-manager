package com.task.manager.controller;

import com.task.manager.entity.User;
import com.task.manager.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){

        LOGGER.info("Registration request received for username: {}", user.getUsername());

        LOGGER.info("Encoding password for username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        LOGGER.info("Assigning default role USER to username: {}", user.getUsername());
        user.setRole("USER");

        LOGGER.info("Saving user to database for username: {}", user.getUsername());
        User savedUser = userRepository.save(user);

        LOGGER.info("User registered successfully. userId: {}, username: {}", savedUser.getId(), savedUser.getUsername());

        return savedUser;
    }
}
