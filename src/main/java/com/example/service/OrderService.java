package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.domain.CreditCardRequest;
import com.example.domain.CreditCardResponse;
import com.example.domain.Order;
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
	
//	@Autowired
//	private RestTemplate restTemplate;
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
		
		orderInCart.setDestinationName(destinationOrder.getDestinationName());
		orderInCart.setDestinationEmail(destinationOrder.getDestinationEmail());
		orderInCart.setDestinationZipCode(destinationOrder.getDestinationZipCode());
		orderInCart.setDestinationAddress(destinationOrder.getDestinationAddress());
		orderInCart.setDestinationTel(destinationOrder.getDestinationTel());
		orderInCart.setDeliveryTime(destinationOrder.getDeliveryTime());
		orderInCart.setPaymentMethod(destinationOrder.getPaymentMethod());
				
		orderRepository.update(orderInCart);
	}
	
	public CreditCardResponse CreditCardPayment(CreditCardRequest creditCardRequest) {
		System.out.println("クレジットカードのサービスクラスのメソッド呼び出し");
		String url = "http://153.127.48.168:8080/sample-credit-card-web-api/credit-card/payment";
		return restTemplate.postForObject(url, creditCardRequest, CreditCardResponse.class);
	}
}
