package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.User;
import com.example.form.InsertShoppingCartForm;
import com.example.service.InsertShoppingCartService;

/**
 * 
 * ショッピングカートに挿入するためのコントローラ.
 * 
 * @author takuya.matsura
 *
 */
@Controller
@RequestMapping("/cart")
public class InsertShoppingCartController {
	private static int nullUserId = 0;
	
	@Autowired
	private InsertShoppingCartService insertShoppingCartService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	private InsertShoppingCartForm setUpForm() {
		return new InsertShoppingCartForm();
	}
	
	@RequestMapping("/insert")
	public String insert(InsertShoppingCartForm form, Model model) {
		System.out.println(form.getItemId());
		System.out.println(form.getQuantity());
		System.out.println(form.getSize());
		System.out.println(form.getToppingList());
		
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getItemId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		
		List<OrderTopping> orderToppingList = new ArrayList<>();
		try {
			for (Integer toppingId : form.getToppingList()) {
				Topping topping = insertShoppingCartService.searchToppingByToppingId(toppingId);
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(toppingId);
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
				
			}
		} catch (NullPointerException e) {
			orderToppingList = new ArrayList<>();
		}
		orderItem.setOrderToppingList(orderToppingList);
		
		User user = (User) session.getAttribute("user");
		if (user == null) {
			user = new User();
			user.setId(decrementNullUserId());
		}
		insertShoppingCartService.insert(orderItem, user);
		
		return "redirect:/cart/show";
	}
	
	private synchronized static int decrementNullUserId() {
		return --nullUserId;
	}

}
