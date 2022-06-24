package com.example.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginuser) {

		Map<String, Integer> purchasedMap = adminService.showPurchasedCount();

		if (isAdmin(loginuser)) {
			return "/admin";
		}
		return "forward:/";
	}

	/**
	 * 管理者かチェックします.
	 * 
	 * @param loginuser ユーザー情報
	 * @return 管理者の場合true、そうでない場合false
	 */
	private boolean isAdmin(LoginUser loginuser) {

		Collection<GrantedAuthority> authorityList = loginuser.getAuthorities();
		for (GrantedAuthority authority : authorityList) {

			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
}
