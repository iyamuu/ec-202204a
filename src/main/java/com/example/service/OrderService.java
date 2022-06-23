package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Order sessionOrder = showOrder(sessionUser.getId());
		
		System.out.println("order : " + order);
		System.out.println("session order : " + sessionOrder);
		
		
		if (order == null && sessionOrder != null) {
			// sessionユーザーにのみ注文があるとき
			System.out.println("未ログイン時の注文データのみ存在します");
			order = sessionOrder;
			order.setUserId(loginUser.getId());
			orderRepository.update(order);
			
			
		} else if (order != null && sessionOrder != null) {
			// ログインユーザーとセッションユーザーの両方に注文があるとき
			System.out.println("ログイン時と未ログイン時の両方に注文データが存在します");
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
			System.out.println("ログイン時の注文データのみ存在します");
			
		}
		
		//update(loginUser.getId(), order);
		System.out.println("merged order : " + order);
		return order;
	}
}
