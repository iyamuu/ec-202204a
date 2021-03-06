package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録機能のコントローラー.
 * 
 * @author takato.tomizawa
 *
 */
@Controller
@RequestMapping("/user")
public class RegisterUserController {
	
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}
	
	@Autowired
	private RegisterUserService service;

	/**
	 * ユーザー登録画面を表示するルーティング.
	 * 
	 * @param model リクエストスコープ
	 * @return ユーザー登録画面
	 */
	@GetMapping("/signup")
	public String toInsert(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		if (oidcUser != null) {
			model.addAttribute("name", oidcUser.getFullName());
			model.addAttribute("email", oidcUser.getEmail());
		}
		return "signup"; 
	}
	
	/**
	 * ユーザー登録処理をするルーティング.
	 * 
	 * @param form ユーザー登録フォーム
	 * @param result バリデーション結果
	 * @param model リクエストスコープ
	 * @return リダイレクト
	 */
	@PostMapping("/insert")
	public String insert(@Validated RegisterUserForm form, BindingResult result, Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		if (oidcUser != null) {
			form.setName(oidcUser.getFullName());
			form.setEmail(oidcUser.getEmail());
		}
		System.out.println(result);
		if(result.hasErrors()) {
			return toInsert(model, oidcUser);
		}
		
		User user = new User();
		BeanUtils.copyProperties(form, user);
		service.insert(user);
		
		return "redirect:/user/toLogin";
	}
}
