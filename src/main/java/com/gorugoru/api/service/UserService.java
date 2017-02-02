package com.gorugoru.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User insertUser(String name, int age){
		User user = new User(name, age);
		user = userRepository.save(user);
		return user;
	}
	
	public List<User> getUserList(){
		List<User> userList = (List<User>) userRepository.findAll();
		return userList;
	}
}
