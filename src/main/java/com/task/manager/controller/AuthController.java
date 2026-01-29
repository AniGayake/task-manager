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

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> req){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.get("username"),
                            req.get("password")
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationFailedException();
        }

        User user = userRepository.findByUsername(req.get("username"))
                .orElseThrow(() -> new AuthenticationFailedException());

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        return Map.of("token", token);
    }
}
