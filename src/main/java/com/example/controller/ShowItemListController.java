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

	/**
	 * 商品一覧画面を表示.
	 * 
	 * @param model	モデル
	 * @param sort	並び順の設定
	 * @return		商品一覧画面
	 */
	@RequestMapping("/list")
	public String list(Model model, String sort) {
		List<Item> itemList = showItemListService.showList(sort);			
		model.addAttribute("itemList", itemList);

		return "item_list";
	}

	/**
	 * 検索画面へ遷移.
	 * 
	 * @param search_name	検索する名前
	 * @param model			モデル
	 * @return				検索画面
	 */
	@RequestMapping("/search")
	public String search(String search_name, String sort, Model model) {

		List<Item> itemList = showItemListService.search(search_name, sort);

		if (itemList.size() == 0) {
			itemList = showItemListService.showList(sort);
			model.addAttribute("no_item", "該当する商品がありません");
		}
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("search_name", search_name);

		return "item_search";
	}

}
