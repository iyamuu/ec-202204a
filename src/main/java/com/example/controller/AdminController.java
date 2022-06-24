package com.example.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginuser) {
		
		Collection<GrantedAuthority> authorityList = loginuser.getAuthorities();
		for(GrantedAuthority authority : authorityList) {
			
			if(authority.getAuthority().equals("ROLE_ADMIN")) {
				return "/admin";	
			}
		}
		
		return "forward:/";
	}
}
