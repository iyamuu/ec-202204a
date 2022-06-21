package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.service.OrderService;

/**
 * 注文機能のコントローラー.
 * 
 * @author takato.tomizawa
 *
 */
@Controller
@RequestMapping("order")
public class OrderController {

	@ModelAttribute
	private OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@Autowired
	private OrderService service;

	/**
	 * 注文確認画面を表示するルーティング.
	 * 
	 * @param orderId 注文ID
	 * @param model リクエストスコープ
	 * @return　注文確認画面
	 */
	@GetMapping("/confirm")
	public String showOrder(Integer orderId, Model model) {
		Order order = service.showOrder(orderId);
		model.addAttribute("order", order);
		try {
			model.addAttribute("tax", order.getTax());
			model.addAttribute("totalPrice", order.getCalcTotalPrice());
		} catch (NullPointerException e) {
			model.addAttribute("tax", 0);
			model.addAttribute("totalPrice", 0);
		}
		return "/order_confirm";
	}
	
	/**
	 * 注文処理を行うルーティング.
	 * 
	 * @param form 注文フォーム
	 * @param result バリデーション結果
	 * @param model リクエストスコープ
	 * @return 一覧ページ
	 * @throws ParseException 
	 */
	@PostMapping("/update")
	public String update(@Validated OrderForm form, BindingResult result, Model model) throws ParseException {
		Timestamp formDeliveryTime = null;
		
		if(!form.getDeliveryTime().equals("")) {
			formDeliveryTime = transformStringToTimestamp(form.getDeliveryTime());
			
			if(canDelivery(formDeliveryTime)) {
				FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTime", "今から3時間後の日時をご入力ください");
				result.addError(fieldError);
			}
		}
		
		if(result.hasErrors()) {
			return showOrder(form.getOrderId(), model);
		}
		
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		order.setDeliveryTime(formDeliveryTime);
		
		service.update(order);
		// TODO: 注文完了画面へリンクを変更する
		return "redirect:/ex-202204a/";
	}
	
	private Timestamp transformStringToTimestamp(String strDeliveryTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = sdf.parse(strDeliveryTime.replace("T", " "));
		
		Timestamp formDeliveryTime = new Timestamp(date.getTime());
		
		return formDeliveryTime;
	}
			
	private boolean canDelivery(Timestamp formDeliveryTime) {
		LocalDateTime now = LocalDateTime.now();
		Timestamp compareTime = Timestamp.valueOf(now.plusHours(3));
		return formDeliveryTime.before(compareTime);
	}
}
