package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.services.UserService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/user")
public class AuthController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtutil;
	
	 @PostMapping("/register")
	    public User register(@RequestBody User user) {
	        return userService.saveUser(user);
	    }

	 
	 @PostMapping("/login")
	 public Map<String, Object> login(@RequestBody User user) {
	     User success = userService.login(user.getName(), user.getPassword());
	     System.out.println("user  "+ user.getName()+ "  password " + user.getPassword());
	     if(success!=null) {
	         String token = jwtutil.generateToken(success.getName(), success.getRole());
	         System.out.println("token is  "+ token);
	         System.out.println("userrole is"+ success.getRole());
	         Map<String, Object> response = new HashMap<>();
	         response.put("token", token);
	         response.put("role", success.getRole());
     System.out.println("role is"+ success.getRole());
     System.out.println("response is "+ response );
	         return response;
	         
	     } else {
	         return (Map<String, Object>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	     }
	 }
	
	

}
