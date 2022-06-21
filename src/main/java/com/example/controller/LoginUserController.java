package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/user")
public class LoginUserController {

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private HttpSession session;

	/**
	 * フォームオブジェクトをリクエストスコープに格納する
	 * 
	 * @return ログインフォーム
	 */
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
		
		// 既にログインしているユーザーは一覧画面に遷移
		if (session.getAttribute("user") != null) {
			return "redirect:/items/list";
		}
		return "login";
	}

	/**
	 * ログインする.
	 * 
	 * @param form 入力されたメールアドレスとパスワード
	 * @return 商品一覧ページ
	 */
	@PostMapping("/login")
	public String login(LoginUserForm form, Model model) {

		User user = loginUserService.login(form.getEmail(), form.getPassword());
		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}

		session.setAttribute("user", user);
		return "redirect:/items/list";
	}

	/**
	 * ログアウトする.
	 * 
	 * @return 商品一覧画面
	 */
	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "redirect:/items/list";
	}
}
