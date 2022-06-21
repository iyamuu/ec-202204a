package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order showOrder(Integer orderId) {
		Order order = new Order();
		try {
			order = orderRepository.findById(orderId).get(0);
		} catch (IndexOutOfBoundsException e) {
			order = null;
		}
		return order;
	}

	public void update(Order order) {
		// ユーザーのIDとstatusが0のorder情報を取得する
		// 今ユーザー入力した情報と上の情報を合わせてリポジトリに投げる
		orderRepository.save(order);
	}
}
