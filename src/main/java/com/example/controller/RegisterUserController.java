package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterUserForm;

@Controller
@RequestMapping("/user")
public class RegisterUserController {
	
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	@GetMapping("/sighup")
	public String toInsert(Model model) {
		return "sighup"; 
	}
	
	@PostMapping("/insert")
	public String insert(@Validated RegisterUserForm form, BindingResult result, Model model) {
		// TODO:商品一覧ページが出来たら、そこに飛ばす
		
		if(result.hasErrors()) {
			return toInsert(model);
		}
		
		return "redirect:/user/sighup";
	}
}
