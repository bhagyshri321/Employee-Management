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

        // 🔹 Use getRequestURI() to match full path, including proxies
        String path = request.getRequestURI();
        System.out.println("JwtFilter sees request path: " + path); // debug log

        // 🔹 Skip public endpoints (exact + trailing slash)
        if (path.equals("/user/register") || path.equals("/user/login") ||
            path.startsWith("/user/register/") || path.startsWith("/user/login/")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔹 JWT validation
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            // TODO: validate the token properly using JwtUtil
            // Example: jwtUtil.validateToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 if invalid
            response.getWriter().write("Invalid token");
            return;
        }

        // 🔹 Proceed with the request
        chain.doFilter(request, response);
    }
}