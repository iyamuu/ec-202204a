package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.OrderRepository;

/**
 * カートに追加された商品の削除を行うサービス
 * 
 * @author keisuke.isoda
 *
 */
@Service
public class DeleteShoppingCartService {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * カートに追加された商品を削除します.
	 * 
	 * @param orderItemId カートに追加された商品のid
	 */
	public void delete(Integer orderItemId) {
		orderRepository.deleteById(orderItemId);
	}
}
