package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品一覧情報を表示するコントローラ.
 * 
 * @author kohei.yamamura
 *
 */
@Controller
@RequestMapping("/items")
public class ShowItemListController {
	
	@Autowired
	ShowItemListService showItemListService;
	
	
	@RequestMapping("/list")
	public String list(String name, Model model) {
		
		List<Item> itemList =  showItemListService.showList();
		
		if(name == "") {
			
			
		}
		
		model.addAttribute("itemList", itemList);
		
		
		return "item_list";
	}
}
