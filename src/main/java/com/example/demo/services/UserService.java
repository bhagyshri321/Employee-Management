package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	public User login(String name, String password) {
		User user= userRepo.findByNameAndPassword(name, password);
		if(user==null) {
			System.out.println("user is: "+ user);
			throw new RuntimeException("Invalid Credentials");
		}
		
		return user;
	}

	public User saveUser(User user) {
		user.setRole(user.getRole());
		return userRepo.save(user);
	}
	

}
