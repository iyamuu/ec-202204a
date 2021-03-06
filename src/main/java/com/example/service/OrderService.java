package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.domain.CreditCardRequest;
import com.example.domain.CreditCardResponse;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.repository.OrderRepository;

/**
 * 注文関係のサービス.
 * 
 * @author takato.tomizawa
 *
 */
@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	
	/**
	 * 注文確認画面の表示.
	 * 
	 * @param userId ユーザーID
	 * @return　注文中の商品一覧
	 */
	public Order showOrder(Integer userId) {
		Order order = new Order();
		try {
			order = orderRepository.findByUserIdAndStatus(userId, 0).get(0);
		} catch (IndexOutOfBoundsException e) {
			order = null;
		}
		return order;
	}

	/**
	 * 注文機能.
	 * 
	 * @param userId ユーザーID
	 * @param destinationOrder　注文宛先情報
	 */
	public void update(Integer userId, Order destinationOrder) {
		Order orderInCart = orderRepository.findByUserIdAndStatus(userId, 0).get(0);
		
		orderInCart.setUserId(userId);
		orderInCart.setOrderDate(new Date());
		orderInCart.setStatus(destinationOrder.getPaymentMethod());
		orderInCart.setTotalPrice(destinationOrder.getTotalPrice());
		
		orderInCart.setDestinationName(destinationOrder.getDestinationName());
		orderInCart.setDestinationEmail(destinationOrder.getDestinationEmail());
		orderInCart.setDestinationZipCode(destinationOrder.getDestinationZipCode());
		orderInCart.setDestinationAddress(destinationOrder.getDestinationAddress());
		orderInCart.setDestinationTel(destinationOrder.getDestinationTel());
		orderInCart.setDeliveryTime(destinationOrder.getDeliveryTime());
		orderInCart.setPaymentMethod(destinationOrder.getPaymentMethod());
		
		orderRepository.update(orderInCart);
	}
	
	/**
	 * クレジットカードの決済処理を行います.
	 * OrderControllerのupdateメソッドで使用
	 * 
	 * @param creditCardRequest
	 * @return
	 */
	public CreditCardResponse CreditCardPayment(CreditCardRequest creditCardRequest) {
		
		String url = "http://153.127.48.168:8080/sample-credit-card-web-api/credit-card/payment";
		return restTemplate.postForObject(url, creditCardRequest, CreditCardResponse.class);
	}

	/**
	 * 
	 * 未ログイン時の注文と過去のログイン時の注文を合体する.
	 * 
	 * @param loginUser ログインユーザー
	 * @param sessionUser　未ログイン時のユーザー
	 * @return　合体した注文
	 */
	public Order mergeOrder(User loginUser, User sessionUser) {
		Order order = showOrder(loginUser.getId());
		Order sessionOrder = null;
		try {
			sessionOrder = showOrder(sessionUser.getId());			
		} catch (NullPointerException e) {
			sessionOrder = null;
		}
		
		
		if (order == null && sessionOrder != null) {
			// sessionユーザーにのみ注文があるとき
			order = sessionOrder;
			order.setUserId(loginUser.getId());
			orderRepository.update(order);
			
			
		} else if (order != null && sessionOrder != null) {
			// ログインユーザーとセッションユーザーの両方に注文があるとき
			List<OrderItem> orderItemList = order.getOrderItemList();
			List<OrderItem> sessionOrderItemList = sessionOrder.getOrderItemList();
			
			// sessionの注文をログインユーザーの注文リストに追加する
			for (OrderItem sessionOrderItem : sessionOrderItemList) {
				orderItemList.add(sessionOrderItem);
				sessionOrderItem.setOrderId(order.getId());
				orderRepository.InsertOrderItem(sessionOrderItem);
			}
			order.setOrderItemList(orderItemList);	
		} else {
			// ログインユーザーにのみ注文が存在するとき
			
		}

		return order;
	}
	
	/**
	 * カート内の商品数を計算する.
	 * 
	 * @param userId ユーザーID
	 * @return　商品数
	 */
	public Integer calcItemCountInCart(Integer userId) {
		Order order = new Order();
		try {
			order = orderRepository.findByUserIdAndStatus(userId, 0).get(0);
		} catch (IndexOutOfBoundsException e) {
			order = null;
		}
		
		Integer itemCount = null;
		if(order == null) {
			itemCount = 0;
		} else {
			itemCount = order.getOrderItemList().size();			
		}
		return itemCount;
	}
}
