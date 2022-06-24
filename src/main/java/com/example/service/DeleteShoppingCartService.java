package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
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
		
		Integer itemCount = order.getOrderItemList().size();
		return itemCount;
	}
}
