package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.domain.CreditCardRequest;
import com.example.domain.Order;
import com.example.domain.User;
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
	
	@Autowired
	private HttpSession session;

	/**
	 * 注文確認画面を表示するルーティング.
	 * 
	 * @param orderId 注文ID
	 * @param model リクエストスコープ
	 * @return　注文確認画面
	 */
	@GetMapping("/confirm")
	public String showOrder(Model model) {
		User user = (User) session.getAttribute("user");
		Order order = service.showOrder(user.getId());
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
		
		System.out.println(form.toString());
		
		// クレジットカードの処理
		// 後でサービスに移動
//		String url = "http://153.127.48.168:8080/sample-credit-card-web-api/credit-card/payment";
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
		
		if(form.getPaymentMethod() == 2) {
			// クレジットカードの決済処理
			CreditCardRequest creditCardRequest = new CreditCardRequest();
			
//			creditCard			
		}
		
		if(!form.getDeliveryTime().equals("")) {
			formDeliveryTime = transformStringToTimestamp(form.getDeliveryTime());
			
			if(canDelivery(formDeliveryTime)) {
				FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTime", "今から3時間後の日時をご入力ください");
				result.addError(fieldError);
			}
		}
		
		if(result.hasErrors()) {
			return showOrder(model);
		}
		
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		order.setDeliveryTime(formDeliveryTime);
		
		User user = (User) session.getAttribute("user");
		service.update(user.getId(), order);
		return "redirect:/order/finished";
	}
	
	/**
	 * 注文完了画面のルーティング.
	 * 
	 * @return 注文完了画面
	 */
	@GetMapping("/finished")
	public String finished() {
		return "/order_finished";
	}
	
	/**
	 * StringからTimestampへの型変更.
	 * 
	 * @param strDeliveryTime String型の年月日時分
	 * @return Timestamp型の年月日時分
	 * @throws ParseException
	 */
	private Timestamp transformStringToTimestamp(String strDeliveryTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = sdf.parse(strDeliveryTime.replace("T", " "));
		
		Timestamp formDeliveryTime = new Timestamp(date.getTime());
		
		return formDeliveryTime;
	}
			
	/**
	 * 配達日時が注文時刻より3時間前か後かを判別する.
	 * 
	 * @param formDeliveryTime
	 * @return 3時間より前だとtrue(そうでなければfalse)を返す
	 */
	private boolean canDelivery(Timestamp formDeliveryTime) {
		LocalDateTime now = LocalDateTime.now();
		Timestamp compareTime = Timestamp.valueOf(now.plusHours(3));
		return formDeliveryTime.before(compareTime);
	}
}
