package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザーログインの業務処理を行う.
 * 
 * @author keisuke.isoda
 *
 */
@Service
public class LoginUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ログインをする.
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public User login(String email, String password) {
		
		User user = userRepository.findByEmailAndPassword(email, password);
		return user;
	}
}
