package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.CreditCardRequest;
import com.example.domain.CreditCardResponse;
import com.example.domain.LoginUser;
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

	@Autowired
	private JavaMailSender mailSender;


	/**
	 * 注文確認画面を表示するルーティング.
	 * 
	 * @param orderId 注文ID
	 * @param model   リクエストスコープ
	 * @return 注文確認画面
	 */
	@GetMapping("/confirm")
	public String showOrder(Model model, @AuthenticationPrincipal LoginUser loginuser) {
		// ログインユーザーの取得
		User user = loginuser.getUser();

		// ログインしていなかった時のユーザー情報を取得
		User sessionUser = (User) session.getAttribute("user");
		session.removeAttribute("user");

		Order order = service.mergeOrder(user, sessionUser);

		order = service.showOrder(user.getId());

		model.addAttribute("order", order);
		try {
			model.addAttribute("tax", order.getTax());
			model.addAttribute("totalPrice", order.getCalcTotalPrice() + order.getTax());
		} catch (NullPointerException e) {
			model.addAttribute("tax", 0);
			model.addAttribute("totalPrice", 0);
		}
		
		Integer itemCount = service.calcItemCountInCart(loginuser.getUser().getId());
		session.setAttribute("itemCount", itemCount);
		return "/order_confirm";
	}

	/**
	 * 注文処理を行うルーティング.
	 * 
	 * @param form   注文フォーム
	 * @param result バリデーション結果
	 * @param model  リクエストスコープ
	 * @return 一覧ページ
	 * @throws ParseException
	 */
	@PostMapping("/update")
	public String update(@Validated OrderForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginuser) throws ParseException {
		Timestamp formDeliveryTime = null;
		
		boolean isCardError = false;

		// クレジットカードの決済処理
		if (form.getPaymentMethod() != null && form.getPaymentMethod() == 2) {
			CreditCardRequest creditCardRequest = new CreditCardRequest();

			// リクエスト用のドメインに値をセット
			creditCardRequest.setCard_number(form.getCardNumber());
			creditCardRequest.setCard_exp_month(form.getCardExpMonth());
			creditCardRequest.setCard_exp_year(form.getCardExpYear());
			creditCardRequest.setCard_cvv(form.getCardCvv());
			creditCardRequest.setCard_name(form.getCardName());

			CreditCardResponse creditCardResponse = service.CreditCardPayment(creditCardRequest);

			if (creditCardResponse.getStatus().equals("error")) {
				model.addAttribute("creditCardError", "クレジットカード情報が不正です");
				isCardError = true;
			}
		}

		if (!form.getDeliveryTime().equals("")) {
			formDeliveryTime = transformStringToTimestamp(form.getDeliveryTime());

			if (canDelivery(formDeliveryTime)) {
				FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTime", "今から3時間後の日時をご入力ください");
				result.addError(fieldError);
			}
		}

		if (result.hasErrors() || isCardError) {
			return showOrder(model, loginuser);
		}

		// ユーザーIDを取得
		User user = loginuser.getUser();
		
		Order order = service.showOrder(user.getId());
		BeanUtils.copyProperties(form, order);
		
		order.setDeliveryTime(formDeliveryTime);
		Integer totalPriceInTax = order.getCalcTotalPrice() + order.getTax();
		order.setTotalPrice(totalPriceInTax);

		service.update(user.getId(), order);
		return "redirect:/order/finished";
	}

	/**
	 * 注文完了画面のルーティング.
	 * 
	 * @return 注文完了画面
	 */
	@GetMapping("/finished")
	public String finished(Model model, @AuthenticationPrincipal LoginUser loginuser) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		
		try {
		      helper = new MimeMessageHelper(message, true);
		      helper.setFrom("r9r.celtics.t9t@gmail.com");
		      helper.setTo(loginuser.getUser().getEmail());
		      helper.setSubject("ラクラクアロハからお買い物内容の確認メールです！");

		      String insertMessage = "この度はラクラクアロハでお買い物して頂き、<br>ありがとうございます！<br><br>注文内容の確認をお願いします！<br>詳しい詳細は以下のリンクをご確認ください <br><br> <a href=\"localhost:8080/ec-202204a/log?userId="+ loginuser.getUser().getId() +"\">注文詳細の確認</a>";

		      helper.setText(insertMessage, true);

		      mailSender.send(message);

		    } catch (Exception e) {
		      e.printStackTrace();
		      return null;
		    }

		Integer itemCount = service.calcItemCountInCart(loginuser.getUser().getId());
		session.setAttribute("itemCount", itemCount);
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
