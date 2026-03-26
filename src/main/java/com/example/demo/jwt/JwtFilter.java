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

    	String path = request.getRequestURI(); // use getRequestURI instead of getServletPath
    	System.out.println("JwtFilter sees request path: " + request.getRequestURI());
    	if (path.equals("/user/register") || path.equals("/user/login") ||
    	    path.startsWith("/user/register/") || path.startsWith("/user/login/")) {
    	    chain.doFilter(request, response);
    	    return;
    	}

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            return;
        }

        String token = authHeader.substring(7);

        try {
            // TODO: validate token
            // e.g., jwtUtil.validateToken(token)
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
            return;
        }

        chain.doFilter(request, response);
    }
}