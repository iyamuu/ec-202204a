package com.example.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.form.InsertItemForm;
import com.example.service.AdminService;

/**
 * 管理者ページのコントローラー
 * 
 * @author isodakeisuke
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@ModelAttribute
	private InsertItemForm setUpForm() {
		return new InsertItemForm();
	}

	/**
	 * 管理者画面を表示します.
	 * 
	 * @param loginuser ユーザー情報
	 * @return 管理者画面
	 */
	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginuser, Model model) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}

		Map<String, Integer> purchasedMap = adminService.showPurchasedCount();
		model.addAttribute("purchasedMap", purchasedMap);
		return "admin";
	}
	
	/**
	 * 商品登録画面を表示します.
	 * 
	 * @param loginuser ユーザー情報（このページに入れるかの認証に使用）
	 * @param form 商品情報
	 * @return 管理者なら商品登録画面　その他なら商品一覧画面
	 */
	@RequestMapping("/toInsertItem")
	public String toInsertItem(@AuthenticationPrincipal LoginUser loginuser) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}
		return "insert_item";
	}
	
	@RequestMapping("/insertItem")
	public String insertItem(@AuthenticationPrincipal LoginUser loginuser, @Validated InsertItemForm form, BindingResult result) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}
		if(result.hasErrors()) {
			return toInsertItem(loginuser);
		}
		
		return "redirect:/admin";
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
