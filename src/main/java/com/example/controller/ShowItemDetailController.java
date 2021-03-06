package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.InsertShoppingCartForm;
import com.example.service.ShowItemDetailService;

@Controller
@RequestMapping("/items")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	@ModelAttribute
	private InsertShoppingCartForm setUpForm() {
		return new InsertShoppingCartForm();
	}
	
	@RequestMapping("/detail")
	public String detail(Integer id, Model model) {
		Item item = showItemDetailService.ShowDetail(id);
		model.addAttribute("item", item);
		
		return "item_Detail.html";
	}

}
