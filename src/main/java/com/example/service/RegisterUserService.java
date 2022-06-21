package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.RegisterUserRepository;

/**
 * ユーザー登録処理をするサービス.
 * 
 * @author takato.tomizawa
 *
 */
@Service
public class RegisterUserService {
	
	@Autowired
	private RegisterUserRepository repository;

	/**
	 * ユーザー登録処理を行う.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		repository.save(user);
	}
}
