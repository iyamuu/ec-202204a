package com.example.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.User;
import com.example.repository.OrderRepository;
import com.example.repository.ToppingRepository;

/**
 * 
 * ショッピングカートに挿入するサービス.
 * 
 * @author takuya.matsura
 *
 */
@Service
@Transactional
public class InsertShoppingCartService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	/**
	 * 
	 * toppingsテーブルをIDで検索します.
	 * 
	 * @param id toppingID
	 * @return topping
	 */
	public Topping searchToppingByToppingId(Integer id) {
		return toppingRepository.findById(id);
	}
	
	/**
	 * 
	 * 一つのオーダーアイテムと複数のオーダートッピングをまとめて挿入します.
	 * 
	 * @param orderItem　注文商品
	 * @param userId　ユーザーID
	 */
	public void insert(OrderItem orderItem, User user) {
		Order order = new Order();
		
		try {
			order = orderRepository.findByUserIdAndStatus(user.getId(), 0).get(0);
		} catch (IndexOutOfBoundsException e) {
			//e.printStackTrace();
			order.setUserId(user.getId());
			order.setStatus(0);
			order.setUser(user);
			order.setTotalPrice(order.getCalcTotalPrice());
			orderRepository.InsertOrder(order);
			
		}
		order = orderRepository.findByUserIdAndStatus(user.getId(), 0).get(0);
		orderItem.setOrderId(order.getId());
		orderRepository.InsertOrderItem(orderItem);
		order = orderRepository.findByUserIdAndStatus(user.getId(), 0).get(0);
		for (OrderTopping orderTopping : orderItem.getOrderToppingList()) {
			
			// オーダートッピングにオーダーアイテムIDをセット
			orderTopping.setOrderItemId(order.getOrderItemList().get(order.getOrderItemList().size()-1).getId());
			orderRepository.InsertOrderTopping(orderTopping);
		}

	}
	
	/**
	 * 
	 * ログインしていないための不使用のマイナスの値のユーザーIDを返します.
	 * 
	 * @return 重複しない未使用のユーザーID
	 */
	public int searchNotUseUserIdInOrder() {
		Random rand = new Random();
		int num = 0;
		while(true) {
			num = rand.nextInt(10000) * (-1);
			if (num < 0) {
				System.out.println("randomnum:" + num);
				System.out.println(orderRepository.findByUserIdAndStatus(num, 0));
				if (orderRepository.findByUserIdAndStatus(num, 0).size() == 0) {
					break;
				}	
			}
		}
		return num;
	}
}
