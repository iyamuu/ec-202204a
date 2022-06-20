package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class RegisterUserController {

	@GetMapping("/sighup")
	public String toInsert() {
		return "sighup"; 
	}
	
	@PostMapping("/insert")
	public String insert() {
		System.out.println("hey");
		return "redirect:/user/sighup";
	}
}
