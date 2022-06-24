package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.ItemRepository;
import com.example.repository.OrderRepository;

@Service
public class AdminService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 購入された商品の個数を表示します.
	 * 
	 * @return 商品名とその個数のMap
	 */
	public Map<String, Integer> showPurchasedCount() {
		
		List<Order> orderList = orderRepository.findOrdered();
		List<Item> itemList = itemRepository.findAll(null);
		
		Map<String, Integer> purchasedMap = new HashMap<>();
		
		for(Item item: itemList) {
			purchasedMap.put(item.getName(), 0);
		}
		
		for(Order order: orderList) {
			for(OrderItem orderItem: order.getOrderItemList()) {
				
				for(String itemName: purchasedMap.keySet()) {
					if(itemName.equals(orderItem.getItem().getName()) ) {
						Integer purchasedCount = purchasedMap.get(itemName) + 1;
						purchasedMap.put(itemName, purchasedCount);
					}
				}
			}
		}
		
		for(String itemName: purchasedMap.keySet()) {
			Integer itemCount = purchasedMap.get(itemName);
			if(itemCount >= 0) {
				System.out.println(itemName + "の購入数は" + itemCount + "です");				
			}
		}
		return purchasedMap;
	}
}
