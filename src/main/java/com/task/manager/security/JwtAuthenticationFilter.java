package com.task.manager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.manager.exception.ApiError;
import com.task.manager.exception.UserNotFoundExceptionTaskAPP;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }catch (UserNotFoundExceptionTaskAPP ex) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");

            ApiError error = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    "USER_NOT_FOUND",
                    ex.getMessage(),
                    request.getRequestURI()
            );

            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
            return;
        } catch (SignatureException ex) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            ApiError error = new ApiError(
                    HttpStatus.UNAUTHORIZED.value(),
                    "INVALID_JWT_SIGNATURE",
                    "Invalid JWT signature. Token cannot be trusted.",
                    request.getRequestURI()
            );

            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
