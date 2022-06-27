package com.example.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.ItemRepository;
import com.example.repository.OrderRepository;

/**
 * 管理者に関連する業務処理を行います.
 * @author isodakeisuke
 *
 */
@Service
public class AdminService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 購入された商品の個数を表示します.
	 * 
	 * @return 商品名とその個数のMap（購入数の多い順に並び替え）
	 * 
	 */
	public Map<String, Integer> showPurchasedCount() {
		
		List<Order> orderList = orderRepository.findOrdered();
		List<Item> itemList = itemRepository.findAll(null);
		
		// 商品名と購入数が入ったmapを作成
		Map<String, Integer> purchasedMap = new HashMap<>();
		
		for(Item item: itemList) {
			purchasedMap.put(item.getName(), 0);
		}
		
		for(Order order: orderList) {
			for(OrderItem orderItem: order.getOrderItemList()) {
				
				for(String itemName: purchasedMap.keySet()) {
					if(itemName.equals(orderItem.getItem().getName()) ) {
						Integer purchasedCount = purchasedMap.get(itemName) + orderItem.getQuantity();
						purchasedMap.put(itemName, purchasedCount);
					}
				}
			}
		}
		
		// mapを購入数の多い順に並び変えて新しいmapを作成
		Map<String, Integer> purchasedSortMap = new LinkedHashMap<>();
		int purchasedMapLenght = purchasedMap.size();

		for (int i = 0; i < purchasedMapLenght; i++) {
			String maxPurchasedName = null;
			Integer maxPurchasedCount = null;

			for (String itemName : purchasedMap.keySet()) {
				if (maxPurchasedCount == null || purchasedMap.get(itemName) > maxPurchasedCount) {
					maxPurchasedName = itemName;
					maxPurchasedCount = purchasedMap.get(itemName);
				} 
			}
			
			purchasedSortMap.put(maxPurchasedName, maxPurchasedCount);
			purchasedMap.remove(maxPurchasedName);
		}
		
		return purchasedSortMap;
	}
}
