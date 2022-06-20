package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.RegisterUserRepository;

@Service
public class RegisterUserService {
	
	@Autowired
	private RegisterUserRepository repository;

	public void insert(User user) {
		repository.save(user);
	}
}
