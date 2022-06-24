package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.service.ShowItemListService;

/**
 * 商品一覧情報を表示するコントローラ.
 * 
 * @author kohei.yamamura
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 商品一覧画面を表示.
	 * 
	 * @param model	モデル
	 * @param sort	並び順の設定
	 * @return		商品一覧画面
	 */
	@RequestMapping("")
	public String list(Model model, String sort,  @AuthenticationPrincipal LoginUser loginuser) {
		List<Item> itemList = showItemListService.showList(sort);		
		model.addAttribute("itemList", itemList);
		Integer userId = null;
		
		try {
			userId = loginuser.getUser().getId();
		} catch(NullPointerException e) {
			User user = (User) session.getAttribute("user");
			if(user == null) {
				userId = 0;
			} else {
				userId = user.getId();
			}
		}
		
		model.addAttribute("userId", userId);
		Integer itemCount = showItemListService.calcItemCountInCart(userId);
		session.setAttribute("itemCount", itemCount);
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
		
		// オートコンプリート用の全商品情報取得
		List<Item> allItemList = showItemListService.showList(null);			
		model.addAttribute("allItemList", allItemList);

		return "item_search";
	}

}
