package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文履歴を管理するサービス
 * @author isodakeisuke
 *
 */
@Service
public class OrderLogService {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 注文履歴を表示します.
	 * @param userId ユーザーID
	 * @return 注文履歴一覧
	 */
	public List<Order> showOrderHistory(Integer userId) {
		
		List<Order> orderList = orderRepository.findLogByUserId(userId);
		Collections.reverse(orderList);
		return orderList;
	}
}
