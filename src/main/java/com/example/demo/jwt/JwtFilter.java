package com.example.demo.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getServletPath();

        // 🔹 Skip public endpoints
        if (path.equals("/login") || path.equals("/register")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔹 Existing JWT validation logic
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            return;
        }

        String token = authHeader.substring(7);
        // TODO: validate token
        // If valid, set Authentication in SecurityContext
        // else response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        chain.doFilter(request, response);
    }
}