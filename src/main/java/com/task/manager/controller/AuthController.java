package com.task.manager.controller;

import com.task.manager.entity.User;
import com.task.manager.exception.AuthenticationFailedException;
import com.task.manager.repository.UserRepository;
import com.task.manager.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> req){

        LOGGER.info("Login request received");

        try {
            LOGGER.info("Authentication process started for username: {}", req.get("username"));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.get("username"),
                            req.get("password")
                    )
            );

            LOGGER.info("Authentication successful for username: {}", req.get("username"));

        } catch (Exception e) {

            LOGGER.error("Authentication failed for username: {}", req.get("username"), e);

            throw new AuthenticationFailedException();
        }

        LOGGER.info("Fetching user from database for username: {}", req.get("username"));

        User user = userRepository.findByUsername(req.get("username"))
                .orElseThrow(() -> {
                    LOGGER.error("User not found in database for username: {}", req.get("username"));
                    return new AuthenticationFailedException();
                });

        LOGGER.info("User fetched successfully. userId: {}, username: {}", user.getId(), user.getUsername());

        LOGGER.info("Generating JWT token for userId: {}", user.getId());

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        LOGGER.info("JWT token generated successfully for userId: {}", user.getId());

        LOGGER.info("Login process completed successfully for username: {}", req.get("username"));

        return Map.of("token", token);
    }
}
