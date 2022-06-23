package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.LoginUser;
import com.example.domain.User;

@RestController
@RequestMapping("/api")
public class FetchLoginInfoApiController {

	@GetMapping("/login-info")
	public User fetchLoginInfo(@AuthenticationPrincipal LoginUser loginuser) {
		User user = loginuser.getUser();
		user.setPassword("");
		return user;
	}
}
