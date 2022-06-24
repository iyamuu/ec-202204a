package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
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
	
	@Autowired
	private ShowItemDetailController showItemDetailController;
	
	@ModelAttribute
	private InsertShoppingCartForm setUpForm() {
		return new InsertShoppingCartForm();
	}
	
	/**
	 * 
	 * カートに商品を追加します.
	 * 
	 * @param form 商品追加フォーム
	 * @param result　エラーメッセージ
	 * @param model　リクエストスコープ
	 * @return カート一覧画面
	 */
	@RequestMapping("/insert")
	public String insert(@Validated InsertShoppingCartForm form, BindingResult result, Model model, @AuthenticationPrincipal LoginUser loginuser) {
		if (result.hasErrors()) {
			return showItemDetailController.detail(form.getItemId(), model);
		}
		// フォームをもとに注文商品を作成する
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getItemId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		
		// 	注文商品のトッピングリストを作成する
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
		
		
		// ユーザーをログイン情報から取得
		User user = new User();
		try {
			user = loginuser.getUser();
		}catch(NullPointerException e) {
			// ログインしていないときはセッションから以前の注文時に使用した情報を取得
			user = (User) session.getAttribute("user");
			//e.printStackTrace();
			
			// もしsessionにも存在しなかったら新しく情報を作成する
			if (user == null) {
				user = new User();
				// どうにかしてかぶらない未使用のユーザーIDを設定
				//もしユーザーのIDが設定されていなかったら新しく設定する
				user.setId(generateRandomNotUseUserId());
				session.setAttribute("user", user);
			}
			
		}
		
		insertShoppingCartService.insert(orderItem, user);
		Integer itemCount = insertShoppingCartService.calcItemCountInCart(user.getId());
		session.setAttribute("itemCount", itemCount);
		return "redirect:/cart/show";
	}
	
	private int generateRandomNotUseUserId() {
		return insertShoppingCartService.searchNotUseUserIdInOrder();
	}
}
