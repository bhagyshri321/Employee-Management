package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.services.UserService;

@RestController
@CrossOrigin(origins="*") // replace * with frontend URL in production
@RequestMapping("/user")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtutil;
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        User success = userService.login(user.getName(), user.getPassword());

        if(success != null) {
            String token = jwtutil.generateToken(success.getName(), success.getRole());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", success.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", "Invalid credentials"));
        }
    }
}