package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.DeleteShoppingCartService;

/**
 * カートに追加された商品の削除を行うコントローラー.
 * 
 * @author keisuke.isoda
 *
 */
@Controller
@RequestMapping("/cart")
public class DeleteShoppingCartController {
	
	@Autowired
	private DeleteShoppingCartService deleteShoppingCartService;
	
	/**
	 * カートに追加された商品を削除します.
	 * @param orderItemId カートに追加した商品のid
	 * @return ショッピングカートを表示するメソッド
	 */
	@PostMapping("/delete")
	public String delete(Integer orderItemId) {
		deleteShoppingCartService.delete(orderItemId);
		return "redirect:/cart/show";
	}
}
