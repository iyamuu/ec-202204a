package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ユーザーのログインログアウトを行うコントローラー.
 * 
 * @author keisuke.isoda
 *
 */
@Controller
@RequestMapping("/auth")
public class LoginUserController {

	@Autowired
	private LoginUserService loginUserService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	private LoginUserForm setUpForm() {
		return new LoginUserForm();
	}
	
	/**
	 * ログイン画面を表示する.
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String toLogin() {
		return "login";
	}
	
	/**
	 * ログインする.
	 * 
	 * @param form 入力されたメールアドレスとパスワード
	 * @return 商品一覧ページ
	 */
	@PostMapping("/login")
	public String login(LoginUserForm form) {
		
		User user = loginUserService.login(form.getEmail(), form.getPassword());
		session.setAttribute("user", user);
		
		// とりあえず動く形でリスト画面にフォーワード
		// pushするときはリダイレクトに書き換える
		return "item_list";
//		return "redirect:/items/list";
	}
}
