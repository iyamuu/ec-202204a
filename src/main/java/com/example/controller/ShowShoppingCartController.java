package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.InsertShoppingCartService;
import com.example.service.ShowShoppingCartService;

/**
 * 
 * ShoppingCartのコントローラ.
 * 
 * @author takuya.matsura
 *
 */
@Controller
@RequestMapping("/cart")
public class ShowShoppingCartController {
	
	private static int nullUserId = 0;
	
	@Autowired
	private ShowShoppingCartService shoppingCartService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private InsertShoppingCartService insertShoppingCartService;
	
	/**
	 * 
	 * ショッピングカートを表示する.
	 * 
	 * @param model　リクエストスコープ
	 * @return　カート画面
	 */
	@RequestMapping("/show")
	public String showCart(Model model, @AuthenticationPrincipal LoginUser loginuser) {
		User user = new User();
		try {
			user = loginuser.getUser();
			System.out.println("login user : " + user);
		}catch(NullPointerException e) {
			user = (User) session.getAttribute("user");
			
			if (user == null) {
				user = new User();
				user.setId(insertShoppingCartService.searchNotUseUserIdInOrder());
				session.setAttribute("user", user);
				System.out.println("userid" + user.getId());
			}
		}
		Order order = shoppingCartService.showCart(user.getId());
		
		model.addAttribute("order", order);
		try {
			model.addAttribute("tax", order.getTax());
			model.addAttribute("totalPrice", order.getCalcTotalPrice() + order.getTax());
		} catch (NullPointerException e) {
			model.addAttribute("tax", 0);
			model.addAttribute("totalPrice", 0);
		}
		return "cart_list.html";
	}
}