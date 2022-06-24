package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.User;
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
	
	@Autowired
	private HttpSession session;
	
	/**
	 * カートに追加された商品を削除します.
	 * @param orderItemId カートに追加した商品のid
	 * @return ショッピングカートを表示するメソッド
	 */
	@PostMapping("/delete")
	public String delete(Integer orderItemId, @AuthenticationPrincipal LoginUser loginuser) {
		User user = new User();
		try {
			user = loginuser.getUser();
		}catch(NullPointerException e) {
			user = (User) session.getAttribute("user");
		}
		deleteShoppingCartService.delete(orderItemId);
		Integer itemCount = deleteShoppingCartService.calcItemCountInCart(user.getId());
		session.setAttribute("itemCount", itemCount);
		return "redirect:/cart/show";
	}
}
