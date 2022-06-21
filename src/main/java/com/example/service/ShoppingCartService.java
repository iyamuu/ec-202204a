package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 
 * OrderテーブルとOrderItemsテーブルとOrderToppingsテーブルを結合したサービス.
 * 
 * @author takuya.matsura
 *
 */
@Service
@Transactional
public class ShoppingCartService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 
	 * ユーザーIDをもとに未注文の最新のものを返す.
	 * 
	 * @param userid ユーザーID
	 * @return　最新の未注文
	 */
	public Order showCart(Integer userid) {
		Order order = new Order();
		try {
			order = orderRepository.findByUserIdAndStatus(userid, 0).get(0);
		} catch (IndexOutOfBoundsException e) {
			order = null;
		}
		return order;
	}

}
