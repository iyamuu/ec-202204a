package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.OrderLogService;

/**
 * 注文履歴を表示するコントローラー.
 * 
 * @author isodakeisuke
 */
@Controller
@RequestMapping("/log")
public class OrderLogController {

	@Autowired
	private OrderLogService orderHistoryService;
	
	/**
	 * 注文履歴を表示します.
	 * 
	 * @param userId ユーザー	ID
	 * @param model モデル
	 * @return 注文履歴画面
	 */
	@RequestMapping("")
	public String index(Integer userId, Model model) {
		
		// ログインしていなかったらログイン画面にリダイレクト
		if(userId == null) {
			return "redirect:/user/toLogin";
		}
		
		List<Order> orderList = orderHistoryService.showOrderHistory(userId);
		System.out.println("ユーザーid" + userId);
		model.addAttribute("orderList", orderList);
		
		return "order_log";
	}
}
